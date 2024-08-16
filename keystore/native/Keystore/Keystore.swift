import CryptoKit
import Foundation

@objc public class Keystore : NSObject {
    @objc(md5:) public class func md5(data: NSData) -> String {
        let hashed = Insecure.MD5.hash(data: data)
        return hashed.compactMap { String(format: "%02x", $0) }.joined()
    }
    
    
    private static let KEY_ALIAS = "DODAMDODAM_KEY"
    private static let ACCOUNT = "DODAMDODAM"

    private class func getKey256() throws -> SymmetricKey {
        let store = GenericPasswordStore()
        guard let key: SymmetricKey = try store.readKey(account: ACCOUNT) else {
            let symmetricKey = SymmetricKey(size: .bits256)
            try store.storeKey(symmetricKey, account: ACCOUNT)
            return symmetricKey
        }
        return key
    
    }
    
    
    @objc public class func encryptData(data: NSData) throws -> String {
        let key = try getKey256()
        let sealedBox = try AES.GCM.seal(data, using: key)
        return (sealedBox.combined! as NSData).base64EncodedString()
    }
    
    @objc public class func decryptData(data: NSString) throws -> String {
        let encryptData = Data(base64Encoded: data as String)!
        let key = try getKey256()
        let sealedBox = try AES.GCM.SealedBox(combined: encryptData)
        let openBox = try AES.GCM.open(sealedBox, using: key)
        return String(data: openBox, encoding: .utf8)!
    }
    
    
}
