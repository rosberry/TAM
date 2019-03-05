/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.log

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.LogType
import com.rosberry.android.tam.utility.MessageFormatter
import kotlinx.android.synthetic.main.i_log_event.view.*

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class EventViewHolder(
        view: View,
        private val clickListener: ItemClickListener,
        private val messageFormatter: MessageFormatter
) : RecyclerView.ViewHolder(view) {

    fun bind(item: LogEvent) {

        val message = messageFormatter.format(item)
        val maxLines = if (item.isExpanded) Integer.MAX_VALUE else 3

        itemView.eventText.setTextColor(item.type.getColorByType())
        itemView.eventText.maxLines = maxLines
        itemView.eventText.ellipsize = TextUtils.TruncateAt.END
        itemView.eventText.text = message
        itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            clickListener.click(adapterPosition)
        }
        itemView.setOnLongClickListener {
            clickListener.longClick(item)
            true
        }
    }
}

private fun LogType.getColorByType(): Int {
    return when (this) {
        LogType.EVENT -> Color.parseColor("#00E676")
        LogType.ERROR -> Color.parseColor("#D50000")
        LogType.WARNING -> Color.parseColor("#FFF9C4")
        LogType.HTTP -> Color.parseColor("#00E5FF")
    }
}