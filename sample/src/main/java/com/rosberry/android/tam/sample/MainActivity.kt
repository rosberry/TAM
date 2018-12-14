/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.TamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EVENT_TAG = "event_tag"
        private const val ERROR_TAG = "error_tag"

        private const val ITEM_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Tam.init() // Init library in Application class.

        logEventButton.setOnClickListener {
            Tam.event(EVENT_TAG, "event message")
        }

        logErrorButton.setOnClickListener {
            Tam.error(IllegalStateException(ERROR_TAG))
        }

        logWarningButton.setOnClickListener {
            Tam.warning()
        }

        logHttpButton.setOnClickListener {
            val params = mapOf(
                    "Header: " to "header value",
                    "body: " to "body value"
            )
            Tam.http("POST", params)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            this.add(0, ITEM_ID, 0, "Tam Log")
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == ITEM_ID) {
            TamFragment().show(supportFragmentManager, TamFragment.TAG)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
