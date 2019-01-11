/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.f_tam.*
import kotlinx.android.synthetic.main.i_log_event.view.*
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author Alexei Korshun on 01/11/2018.
 */
class TamFragment : DialogFragment(), Tam.EventObserver {

    companion object {
        const val TAG = "TamFragment"
    }

    private lateinit var adapter: EventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_tam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tam.instance()
            .observeEvents(this)
        eventsList.itemAnimator = DefaultItemAnimator()
    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes;
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as android.view.WindowManager.LayoutParams
        super.onResume()
    }

    override fun onDestroyView() {
        Tam.instance()
            .deobserveEvents(this)
        super.onDestroyView()
    }

    override fun events(events: List<Tam.LogEvent>) {
        adapter = EventsAdapter(ArrayList(events))
        eventsList.adapter = adapter
    }

    override fun newEvent(event: Tam.LogEvent) {
        adapter.putEvent(event)
    }
}

class EventsAdapter(
        private val items: MutableList<Tam.LogEvent>
) : RecyclerView.Adapter<EventViewHolder>(), ItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.i_log_event, parent, false)
        return EventViewHolder(view, this)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun click(position: Int) {
        notifyItemChanged(position)
    }

    fun putEvent(item: Tam.LogEvent) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }
}

class EventViewHolder(
        view: View,
        private val clickListener: ItemClickListener
) : RecyclerView.ViewHolder(view) {

    private val timeFormat = SimpleDateFormat("HH:mm:SS", Locale.getDefault())

    fun bind(item: Tam.LogEvent) {
        val time = timeFormat.format(item.time.time)

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

interface ItemClickListener {

    fun click(position: Int)
}