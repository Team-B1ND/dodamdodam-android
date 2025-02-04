import SwiftUI
import DodamTeacher
import FirebaseCore

@main
struct iOSApp: App {
    
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin { _ in
            
        }
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
