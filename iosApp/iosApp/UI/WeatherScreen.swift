import SwiftUI
import shared
import Combine

struct WeatherScreen: View {
    @StateObject private var viewModel = WeatherViewModel(wrapped: StoreWrappers().weatherStoreWrapper())

	var body: some View {
        WeatherScreenContent(state: viewModel.state, enteredText: self.$viewModel.city)
	}
}

struct WeatherScreenContent: View {
    var state: WeatherStore.State
    @Binding var enteredText: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("Weather App")
                .font(.largeTitle)
            TextField("City", text: $enteredText)
                .autocapitalization(.words)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .background(RoundedRectangle(cornerRadius: 10, style: .continuous))
                .accentColor(.blue)
            if(state.isCityWeatherLoading) {
                ProgressView()
                    .progressViewStyle(.circular)
            } else {
                Text("temperature: \(formatTemperature(temperature: state.cityWeather?.temperature)) \(formatTemperatureUnit(temperatureUnit: state.cityWeather?.temperatureUnit))")
            }
        }
    }
}

struct WeatherView_Previews: PreviewProvider {
	static var previews: some View {
		WeatherScreen()
	}
}
