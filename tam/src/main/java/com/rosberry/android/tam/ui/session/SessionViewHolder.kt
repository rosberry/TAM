/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.session

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionViewHolder(
        view: View,
        clickListener: (String) -> Unit
) : RecyclerView.ViewHolder(view) {

    private lateinit var name: String

    init {
        itemView.setOnClickListener { clickListener(name) }
    }

    fun bind(sessionName: String) {
        name = sessionName
        itemView.findViewById<TextView>(android.R.id.text1)
            ?.run {
                text = sessionName
                setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            }
    }
}