//
//  WeatherViewModel.swift
//  iosApp
//
//  Created by Mikhail Kirianov on 23.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import Combine

final class WeatherViewModel: ObservableObject {
    @Published var city: String = ""
    private var wrapped: WeatherStoreWrapper
    @Published private(set) var state: WeatherStore.State
    private var cancellables: Set<AnyCancellable> = Set<AnyCancellable>()
        
    init(wrapped: WeatherStoreWrapper) {
        self.wrapped = wrapped
        state = wrapped.stateFlow.value as! WeatherStore.State
        
        (wrapped.stateFlow.asPublisher() as AnyPublisher<WeatherStore.State, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$state)
        
        $city
            .filter { $0 != "" }
            .debounce(for: 1.0, scheduler: RunLoop.main)
            .removeDuplicates()
            .flatMap { (city:String) -> AnyPublisher <Void, Never> in
                return Deferred {
                    Future<Void, Error> { promise in
                        wrapped.dispatch(action: WeatherStore.ActionUpdateCityName(cityName:city)) { optionalError in
                            if let error = optionalError {
                                promise(.failure(error))
                            } else {
                                promise(.success(()))
                            }
                        }
                    }
                  }
                .replaceError(with: ())
                .eraseToAnyPublisher()
            }
            .sink(
                receiveValue: { value in }
            )
            .store(in: &cancellables)
    }
        
    deinit {
        wrapped.onCleared()
    }
}
