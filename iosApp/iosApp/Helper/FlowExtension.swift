//
//  FlowExtension.swift
//  iosApp
//
//  Created by Mikhail Kirianov on 23.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import Combine

public extension Kotlinx_coroutines_coreFlow {
    func asPublisher<T: AnyObject>() -> AnyPublisher<T, Never> {
        (FlowPublisher(flow: self) as FlowPublisher<T>).eraseToAnyPublisher()
    }
}
