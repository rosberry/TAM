/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.session

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(sessionName: String) {
        itemView.findViewById<TextView>(android.R.id.text1)
            ?.run {
                text = sessionName
                setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
            }
    }

}