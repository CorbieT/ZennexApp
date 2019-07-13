package ru.zennex.zennexapp.common

import com.orhanobut.hawk.Hawk
import java.util.*

object HawkManager {

    private const val LANGUAGE_KEY = "language_key"

    fun getLanguage(): Language {
        return Hawk.get<Language>(LANGUAGE_KEY) ?: if (Locale.getDefault().language == "ru") Language.RUS
        else Language.ENG
    }

    fun saveLanguage(language: Language) {
        Hawk.put(LANGUAGE_KEY, language)
    }
}

enum class Language {
    RUS, ENG
}
