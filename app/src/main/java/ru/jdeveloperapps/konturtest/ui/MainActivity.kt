package ru.jdeveloperapps.konturtest.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import ru.jdeveloperapps.konturtest.R
import ru.jdeveloperapps.konturtest.other.Constants.Companion.KEY_LAST_DOWNLOAD
import ru.jdeveloperapps.konturtest.viewModels.MainViewModel
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if ((Date().time - sharedPreferences.getLong(KEY_LAST_DOWNLOAD, 0L)) > 60000) {
            viewModel.updateData()
        }

        fragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when(destination.id) {
                    R.id.listFragment  ->
                        toolbar.visibility = View.GONE
                    else -> {
                        toolbar.visibility = View.VISIBLE
                    }
                }
            }


    }

    override fun onSupportNavigateUp(): Boolean {
       val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}