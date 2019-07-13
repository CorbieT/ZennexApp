package ru.zennex.zennexapp

import android.app.Application
import com.orhanobut.hawk.Hawk

class ZennexApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}