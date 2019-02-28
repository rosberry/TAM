/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.di

import android.content.ClipboardManager
import android.content.Context
import com.rosberry.android.tam.data.ClipboardRepository
import com.rosberry.android.tam.domain.clipboard.ClipboardInteractor
import com.rosberry.android.tam.presentation.TamPresenter
import com.rosberry.android.tam.utility.MessageFormatter
import com.rosberry.android.tam.utility.TimeFormatter
import org.koin.dsl.module

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */
internal val tamModule = module {

    factory { TimeFormatter() }
    factory { MessageFormatter(get()) }
    factory { get<Context>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    factory { ClipboardRepository(get()) }
    factory { ClipboardInteractor(get(), get()) }
    factory { TamPresenter(get()) }
}