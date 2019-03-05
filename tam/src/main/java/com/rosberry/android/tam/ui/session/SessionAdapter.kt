/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.session

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionAdapter(
        private val items: Array<String>
) : RecyclerView.Adapter<SessionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): SessionViewHolder {
        return SessionViewHolder(LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: SessionViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }
}