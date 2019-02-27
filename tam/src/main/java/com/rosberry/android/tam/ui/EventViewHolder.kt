/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.View
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.utility.TimeFormatter
import kotlinx.android.synthetic.main.i_log_event.view.*

/**
 * @author Alexei Korshun on 27/02/2019.
 */
class EventViewHolder(
        view: View,
        private val clickListener: ItemClickListener,
        private val timeFormatter: TimeFormatter
) : RecyclerView.ViewHolder(view) {

    fun bind(item: Tam.LogEvent) {
        val time = timeFormatter.formatTimeAsLog(item.time.time)

        val logString = if (item.type == Tam.LogType.HTTP) "[$time] ${item.tag}: ${item.message}"
        else "[$time] ${item.tag}:\n${item.message}"

        itemView.eventText.setTextColor(getColorByType(item.type))
        val spannableString = SpannableString(logString)
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(boldSpan, 0, time.length + item.tag.length + 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        val maxLines = if (item.isExpanded) Integer.MAX_VALUE else 3
        itemView.eventText.maxLines = maxLines
        itemView.eventText.ellipsize = TextUtils.TruncateAt.END
        itemView.eventText.text = spannableString

        itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            clickListener.click(adapterPosition)
        }

        itemView.setOnLongClickListener {
            clickListener.longClick(item)
            true
        }
    }

    private fun getColorByType(type: Tam.LogType): Int {
        return when (type) {
            Tam.LogType.EVENT -> Color.parseColor("#00E676")
            Tam.LogType.ERROR -> Color.parseColor("#D50000")
            Tam.LogType.WARNING -> Color.parseColor("#FFF9C4")
            Tam.LogType.HTTP -> Color.parseColor("#00E5FF")
        }
    }
}