package io.github.dev_ritik.politoons.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dev_ritik.politoons.R
import io.github.dev_ritik.politoons.model.Politoon
import kotlinx.android.synthetic.main.toon_layout.view.*

class ToonsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        if (Toons == null) return 0
        return Toons!!.size
    }

    private var Toons: List<Politoon>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.toon_layout, parent, false)

        return ViewHolder(v)
    }

    internal fun swapData(toons: List<Politoon>) {
            Toons = toons
            notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val politoon = Toons!![p1]
        p0.itemView.name.text = politoon.name
        p0.itemView.party.text = politoon.party
        p0.itemView.majorRole.text = politoon.majorRole

    }
}
