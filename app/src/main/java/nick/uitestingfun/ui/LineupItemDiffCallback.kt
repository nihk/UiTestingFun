package nick.uitestingfun.ui

import androidx.recyclerview.widget.DiffUtil
import nick.uitestingfun.data.LineupItem

object LineupItemDiffCallback : DiffUtil.ItemCallback<LineupItem>() {

    override fun areItemsTheSame(oldItem: LineupItem, newItem: LineupItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: LineupItem, newItem: LineupItem): Boolean {
        return oldItem == newItem
    }
}