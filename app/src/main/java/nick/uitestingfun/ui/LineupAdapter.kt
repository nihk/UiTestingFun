package nick.uitestingfun.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import nick.uitestingfun.data.LineupItem

class LineupAdapter : ListAdapter<LineupItem, LineupItemViewHolder>(LineupItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineupItemViewHolder {
        return LineupItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: LineupItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}