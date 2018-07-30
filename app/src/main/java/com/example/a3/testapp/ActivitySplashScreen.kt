package com.example.a3.testapp

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_splash_screen.*

import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Handler
import android.view.Menu


class ActivitySplashScreen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        webViewSplash.loadUrl("file:///android_asset/index.html")

        Handler().postDelayed(Runnable {
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@ActivitySplashScreen, ActivityMainActivity::class.java)
           this.startActivity(mainIntent)
            this.finish()
        }, 3000)





    }
}
