package ru.zennex.zennexapp.activities

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.google.android.material.tabs.TabLayout
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.adapters.MainTabAdapter
import ru.zennex.zennexapp.common.HawkManager
import ru.zennex.zennexapp.common.Language
import ru.zennex.zennexapp.common.Language.*
import ru.zennex.zennexapp.fragments.MainFragment
import ru.zennex.zennexapp.interfaces.OnBackPressedListener
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var currentLanguage: Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentLanguage = HawkManager.getLanguage()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu?.findItem(R.id.action_change_language)?.let { setLanguageIconMenuItem(currentLanguage, it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_change_language -> {
                currentLanguage = if (currentLanguage == RUS) ENG else RUS
                HawkManager.saveLanguage(currentLanguage)
                updateResources(currentLanguage)
                setLanguageIconMenuItem(currentLanguage, item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment is OnBackPressedListener) {
                fragment.onBackPressed()
                return
            }
        }
        super.onBackPressed()
    }

    private fun setLanguageIconMenuItem(currentLanguage: Language, menuItem: MenuItem) {
        menuItem.icon = if (currentLanguage == RUS) getDrawable(R.drawable.russia)
        else getDrawable(R.drawable.united_states)
    }

    private fun updateResources(language: Language) {
        val locale = Locale(if (language == RUS) "ru" else "en")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)

        //TODO MARK
        baseContext.createConfigurationContext(config)
//        recreate()
//        resources.updateConfiguration(config, resources.displayMetrics)
    }

}
