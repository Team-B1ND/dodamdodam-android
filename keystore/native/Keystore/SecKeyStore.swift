/*
See LICENSE folder for this sample’s licensing information.

Abstract:
Methods for storing SecKey convertible items in the keychain.
*/

import Foundation
import CryptoKit
import Security

struct SecKeyStore {
    
    /// Stores a CryptoKit key in the keychain as a SecKey instance.
    func storeKey<T: SecKeyConvertible>(_ key: T, label: String) throws {

        // Describe the key.
        let attributes = [kSecAttrKeyType: kSecAttrKeyTypeECSECPrimeRandom,
                          kSecAttrKeyClass: kSecAttrKeyClassPrivate] as [String: Any]
        
        // Get a SecKey representation.
        guard let secKey = SecKeyCreateWithData(key.x963Representation as CFData,
                                                attributes as CFDictionary,
                                                nil)
            else {
                throw KeyStoreError("Unable to create SecKey representation.")
        }

        // Describe the add operation.
        let query = [kSecClass: kSecClassKey,
                     kSecAttrApplicationLabel: label,
                     kSecAttrAccessible: kSecAttrAccessibleWhenUnlocked,
                     kSecUseDataProtectionKeychain: true,
                     kSecValueRef: secKey] as [String: Any]
        
        // Add the key to the keychain.
        let status = SecItemAdd(query as CFDictionary, nil)
        guard status == errSecSuccess else {
            throw KeyStoreError("Unable to store item: \(status.message)")
        }
   }
    
    /// Reads a CryptoKit key from the keychain as a SecKey instance.
    func readKey<T: SecKeyConvertible>(label: String) throws -> T? {
        
        // Seek an elliptic-curve key with a given label.
        let query = [kSecClass: kSecClassKey,
                     kSecAttrApplicationLabel: label,
                     kSecAttrKeyType: kSecAttrKeyTypeECSECPrimeRandom,
                     kSecUseDataProtectionKeychain: true,
                     kSecReturnRef: true] as [String: Any]
        
        // Find and cast the result as a SecKey instance.
        var item: CFTypeRef?
        var secKey: SecKey
        switch SecItemCopyMatching(query as CFDictionary, &item) {
        case errSecSuccess: secKey = item as! SecKey
        case errSecItemNotFound: return nil
        case let status: throw KeyStoreError("Keychain read failed: \(status.message)")
        }

        // Convert the SecKey into a CryptoKit key.
        var error: Unmanaged<CFError>?
        guard let data = SecKeyCopyExternalRepresentation(secKey, &error) as Data? else {
            throw KeyStoreError(error.debugDescription)
        }
        let key = try T(x963Representation: data)

        return key
    }

    /// Stores a key in the keychain and then reads it back.
    func roundTrip<T: SecKeyConvertible>(_ key: T) throws -> T {
        // A label for the key in the keychain.
        let label = "com.example.seckey.key"
        
        // Start fresh.
        try deleteKey(label: label)
        
        // Store it and then get it back.
        try storeKey(key, label: label)
        guard let key: T = try readKey(label: label) else {
            throw KeyStoreError("Failed to locate stored key.")
        }
        return key
    }
    
    /// Removes any existing key with the given label.
    func deleteKey(label: String) throws {
        let query = [kSecClass: kSecClassKey,
                     kSecUseDataProtectionKeychain: true,
                     kSecAttrApplicationLabel: label] as [String: Any]
        switch SecItemDelete(query as CFDictionary) {
        case errSecItemNotFound, errSecSuccess: break // Ignore these.
        case let status:
            throw KeyStoreError("Unexpected deletion error: \(status.message)")
        }
    }
}
