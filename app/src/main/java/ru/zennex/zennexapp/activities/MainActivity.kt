package ru.zennex.zennexapp.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.common.ContextWrapper
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

        //https://stackoverflow.com/questions/7951730/viewpager-and-fragments-whats-the-right-way-to-store-fragments-state
        val fragment: MainFragment
        if (savedInstanceState != null) {
            fragment = supportFragmentManager.findFragmentByTag("main") as MainFragment
        } else {
            fragment = MainFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment, "main")
                .commit()
        }

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
                setLanguageIconMenuItem(currentLanguage, item)
                updateResources(currentLanguage)
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
        HawkManager.saveLanguage(language)
        recreate()
    }

    override fun attachBaseContext(newBase: Context?) {
        val locale = Locale(if (HawkManager.getLanguage() == RUS) "ru" else "en")
        val context = ContextWrapper.wrap(newBase!!, locale)
        super.attachBaseContext(context)
    }
}
