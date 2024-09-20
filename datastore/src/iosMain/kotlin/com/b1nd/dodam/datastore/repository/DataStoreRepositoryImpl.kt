package com.b1nd.dodam.datastore.repository

import com.b1nd.dodam.datastore.model.User
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform.CoreFoundation.CFAutorelease
import platform.CoreFoundation.CFDictionaryAddValue
import platform.CoreFoundation.CFDictionaryCreateMutable
import platform.CoreFoundation.CFDictionaryRef
import platform.CoreFoundation.CFStringRef
import platform.CoreFoundation.CFTypeRef
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFBooleanFalse
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.CFBridgingRelease
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.SecItemUpdate
import platform.Security.kSecAttrAccessGroup
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleWhenUnlocked
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import platform.darwin.OSStatus

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
class DataStoreRepositoryImpl : DataStoreRepository {

    // use only dodam teacher ios logic
    private val serviceName: String = "com.b1nd.dodam.teacher"

    private val accessibility: CFStringRef? = kSecAttrAccessibleWhenUnlocked

    private val _user = MutableStateFlow(
        User(
            id = value("id") ?: "",
            pw = value("pw") ?: "",
            token = value("token") ?: "",
        ),
    )
    override val user: Flow<User>
        get() = _user

    private val _token = MutableStateFlow(value("token") ?: "")
    override val token: Flow<String>
        get() = _token

    override suspend fun saveUser(id: String, pw: String, token: String) {
        add("id", id)
        add("pw", pw)
        add("token", token)
        _user.value =
            User(
                id = id,
                pw = pw,
                token = token,
            )
        _token.emit(token)
    }

    override suspend fun saveToken(token: String) {
        update("token", token)
        _token.emit(token)
    }

    override suspend fun deleteUser() {
        listOf("id", "pw", "token").forEach {
            delete(it)
        }
        _user.emit(User())
        _token.emit("")
    }

    private fun add(key: String, value: String): Boolean = context(key, value.toNSData()) { (account, data) ->
        val query = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to account,
            kSecValueData to data,
            kSecAttrAccessible to accessibility,
        )

        SecItemAdd(query, null).validate()
    }

    private fun update(key: String, value: String): Boolean = context(key, value.toNSData()) { (account, data) ->
        val query = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to account,
            kSecReturnData to kCFBooleanFalse,
        )

        val updateQuery = query(
            kSecValueData to data,
        )

        SecItemUpdate(query, updateQuery).validate()
    }

    private fun delete(key: String): Boolean = context(key) { (account) ->
        val query = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to account,
        )
        SecItemDelete(query).validate()
    }

    private fun value(forKey: String): String? = context(forKey) { (account) ->
        val query = query(
            kSecClass to kSecClassGenericPassword,
            kSecAttrAccount to account,
            kSecReturnData to kCFBooleanTrue,
            kSecMatchLimit to kSecMatchLimitOne,
        )

        memScoped {
            val result = alloc<CFTypeRefVar>()
            SecItemCopyMatching(query, result.ptr)
            CFBridgingRelease(result.value) as? NSData
        }
    }.let {
        it?.stringValue
    }

    private fun <T> context(vararg values: Any?, block: Context.(List<CFTypeRef?>) -> T): T {
        val standard = mapOf(
            kSecAttrService to CFBridgingRetain(serviceName),
            kSecAttrAccessGroup to CFBridgingRetain(null),
        )
        val custom = arrayOf(*values).map { CFBridgingRetain(it) }
        return block.invoke(Context(standard), custom).apply {
            standard.values.plus(custom).forEach { CFBridgingRelease(it) }
        }
    }

    private fun String.toNSData(): NSData? = NSString.create(string = this).dataUsingEncoding(NSUTF8StringEncoding)

    private val NSData.stringValue: String
        get() = NSString.create(this, NSUTF8StringEncoding) as String

    private fun OSStatus.validate(): Boolean {
        return (this.toUInt() == platform.darwin.noErr)
    }

    private class Context(val refs: Map<CFStringRef?, CFTypeRef?>) {
        fun query(vararg pairs: Pair<CFStringRef?, CFTypeRef?>): CFDictionaryRef? {
            val map = mapOf(*pairs).plus(refs.filter { it.value != null })
            return CFDictionaryCreateMutable(
                null,
                map.size.convert(),
                null,
                null,
            ).apply {
                map.entries.forEach { CFDictionaryAddValue(this, it.key, it.value) }
            }.apply {
                CFAutorelease(this)
            }
        }
    }
}
