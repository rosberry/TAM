/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.log

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.LogType
import com.rosberry.android.tam.utility.shortTimeFormatMessage
import kotlinx.android.synthetic.main.i_log_event.view.*

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class EventViewHolder(
        view: View,
        private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(view) {

    private val maxCollapsedLines = 3

    private lateinit var item: LogEvent

    init {
        itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            clickListener.click(adapterPosition)
        }
        itemView.setOnLongClickListener {
            clickListener.longClick(item)
            true
        }
    }

    fun bind(item: LogEvent) {
        this.item = item
        val message = item.shortTimeFormatMessage()
        val maxLines = if (item.isExpanded) Integer.MAX_VALUE else maxCollapsedLines

        itemView.eventText.setTextColor(item.type.getColorByType())
        itemView.eventText.maxLines = maxLines
        itemView.eventText.ellipsize = TextUtils.TruncateAt.END
        itemView.eventText.text = message
    }

    private fun LogType.getColorByType(): Int = when (this) {
        LogType.EVENT -> Color.parseColor("#00E676")
        LogType.ERROR -> Color.parseColor("#D50000")
        LogType.WARNING -> Color.parseColor("#FFF9C4")
        LogType.HTTP -> Color.parseColor("#00E5FF")
    }
}