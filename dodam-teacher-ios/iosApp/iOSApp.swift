import SwiftUI
import DodamTeacher
import Firebase

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin { _ in
            
        }
        FirebaseApp.configure()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
