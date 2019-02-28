/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.R
import com.rosberry.android.tam.utility.MessageFormatter

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class EventsAdapter(
        private val items: MutableList<LogEvent>,
        private val clickListener: ItemClickListener,
        private val messageFormetter: MessageFormatter
) : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.i_log_event, parent, false)
        return EventViewHolder(view, clickListener, messageFormetter)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun putEvent(item: LogEvent) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }
}