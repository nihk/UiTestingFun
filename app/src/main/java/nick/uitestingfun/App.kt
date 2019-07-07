package nick.uitestingfun

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var instance: Application

        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}