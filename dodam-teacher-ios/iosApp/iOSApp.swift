import SwiftUI
import DodamTeacher
import FirebaseCore
import FirebaseMessaging




class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
      FirebaseApp.configure() //important

      DodamTeacher.AppInitializer.shared.onApplicationStart()
      
    return true
  }
  func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
  }
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any]) async -> UIBackgroundFetchResult {
        NotifierManager.shared.onApplicationDidReceiveRemoteNotification(userInfo: userInfo)
        return UIBackgroundFetchResult.newData
    }
    
}


@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin { _ in
            
        }
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
