/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.R

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class EventsAdapter(
        private val items: MutableList<LogEvent>,
        private val clickListener: ItemClickListener
) : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.i_log_event, parent, false)
        return EventViewHolder(view, clickListener)
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