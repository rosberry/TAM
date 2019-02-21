package com.rosberry.android.tam

import com.rosberry.android.tam.domain.clipboard.ClipboardInteractor
import org.koin.dsl.module

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */
val tamModule = module {

    single { ClipboardInteractor(get()) }

    factory { TamPresenter(get()) }
}