package nick.uitestingfun.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nick.uitestingfun.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LineupFragment())
                .commit()
        }
    }
}
