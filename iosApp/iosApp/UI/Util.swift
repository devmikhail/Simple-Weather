//
//  Util.swift
//  iosApp
//
//  Created by Mikhail Kirianov on 23.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

func formatTemperature(temperature: Double?) -> String {
    if let temperatureValue = temperature {
        return "\(temperatureValue)"
    } else {
        return "n/a"
    }
}

func formatTemperatureUnit(temperatureUnit: TemperatureUnit?) -> String {
    switch temperatureUnit {
    case TemperatureUnit.celsius:
        return "°C"
    case TemperatureUnit.fahrenheit:
        return "°F"
    default:
        return "n/a"
    }
}
