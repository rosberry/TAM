/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author Evgeniy Nagibin on 20/02/2019.
 */
class TamApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TamApp)
            androidLogger()
            modules(tamModule)
        }
    }
}