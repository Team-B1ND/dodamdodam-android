import SwiftUI
import DodamTeacher

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
