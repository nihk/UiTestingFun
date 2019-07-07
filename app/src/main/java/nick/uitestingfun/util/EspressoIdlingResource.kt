package nick.uitestingfun.util

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource : IdlingResource {

    private val countingIdlingResource = CountingIdlingResource("cbc_idling_resource")

    override fun getName(): String = countingIdlingResource.name

    override fun isIdleNow() = countingIdlingResource.isIdleNow

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        countingIdlingResource.registerIdleTransitionCallback(callback)
    }

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}