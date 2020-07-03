package com.arya.apigithubusers.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.arya.apigithubusers.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenctivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, 2000)

        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)

        logo.startAnimation(stb)
        tv_app_name.startAnimation(btt)
    }
}
