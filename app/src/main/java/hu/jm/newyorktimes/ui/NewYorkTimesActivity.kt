package hu.jm.newyorktimes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.jm.newyorktimes.R

@AndroidEntryPoint
class NewYorkTimesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(findNavController(R.id.navFragment))
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.navFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}