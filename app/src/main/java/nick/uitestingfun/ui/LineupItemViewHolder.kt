package nick.uitestingfun.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_lineup.view.*
import nick.uitestingfun.R
import nick.uitestingfun.data.LineupItem
import nick.uitestingfun.util.Colors
import nick.uitestingfun.util.wordCount

class LineupItemViewHolder(
    root: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(root.context)
        .inflate(R.layout.item_lineup, root, false)
) {

    companion object {
        @VisibleForTesting
        fun getBackgroundColor(charSequence: CharSequence): Int {
            return if (charSequence.wordCount > 10) {
                Colors.LIGHT_GREEN
            } else {
                Colors.LIGHT_RED
            }
        }
    }

    fun bind(lineupItem: LineupItem) {
        itemView.headline.text = lineupItem.title
        @ColorInt val color = getBackgroundColor(lineupItem.title)
        itemView.container.setBackgroundColor(color)
    }
}