package ru.virarnd.dogopedia

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.virarnd.dogopedia.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragmentNavigationHost) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.bottomNavView.setupWithNavController(navController)

        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                binding.bottomNavView.selectedItemId -> {
                    Timber.d("MyLog_MainActivity_onCreate: RESELECTED: ${it.itemId}")
                }
                R.id.breed_main_list -> {
                    navController.popBackStack(R.id.breedsListFragment, true)
                    navController.navigate(R.id.breedsListFragment)
                    it.isChecked = true
                }
                R.id.favourite_list -> {
                    navController.popBackStack(R.id.favouritesFragment, true)
                    navController.navigate(R.id.favouritesFragment)
                    it.isChecked = true
                }
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Timber.d("MyLog_MainActivity_onCreate: NEW DESTINATION -> ${destination.id}")
            when (destination.id) {
                R.id.breedsListFragment -> {
                    Timber.d("MyLog_MainActivity_onCreate: NEED MY ICON !!!")
                    hideNavBar(false)
                    showBackArrow(false)
                }
                R.id.favouritesFragment -> {
                    hideNavBar(false)
                    showBackArrow(false)
                }
                else -> {
                    hideNavBar(true)
                    showBackArrow(true)
                }
            }
        }
    }

    private fun hideNavBar(isVisible: Boolean) {
        binding.bottomNavView.visibility = if (isVisible) GONE else VISIBLE
    }

    private fun showBackArrow(isVisible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isVisible)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setTitleText(newTitle: String) {
        binding.toolbarTextView.text = newTitle
    }

    fun startProgress(state: Boolean) {
        if (state) binding.progressNetwork.show() else binding.progressNetwork.hide()
    }

    fun showBasicDialog(view: View?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("The body of my alert")
        builder.show()
    }

}