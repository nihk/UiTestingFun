package nick.uitestingfun.utils

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import nick.uitestingfun.R
import nick.uitestingfun.ui.LineupItemViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}

fun withBackgroundColor(@ColorInt colorInt: Int): Matcher<View> {
    return object : BoundedMatcher<View, View>(View::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has background color $colorInt")
        }

        override fun matchesSafely(item: View): Boolean {
            return colorInt == (item.background as? ColorDrawable)?.color
        }
    }
}

fun withItemCount(itemCount: Int): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has $itemCount items")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return itemCount == view.adapter?.itemCount
        }
    }
}

fun withLineupViewHolderBackgroundColor(): Matcher<View> {
    return object : BoundedMatcher<View, View>(View::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("has LineupViewHolder background color")
        }

        override fun matchesSafely(item: View): Boolean {
            val title: TextView = item.findViewById(R.id.headline)
            val expectedColor = LineupItemViewHolder.getBackgroundColor(title.text)
            return withBackgroundColor(expectedColor).matches(item)
        }
    }
}