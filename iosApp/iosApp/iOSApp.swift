import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        KoinHelperKt.doInitKoin(
            apiKey: Bundle.main.infoDictionary?["API_KEY"] as! String,
            additionalModules: [KoinKt.storeWrappersModule]
        ) { Koin_coreKoinApplication in
        }
    }
    
	var body: some Scene {
		WindowGroup {
			WeatherScreen()
		}
	}
}
