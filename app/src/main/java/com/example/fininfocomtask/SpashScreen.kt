package com.example.fininfocomtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.fininfocomtask.databinding.ActivitySpashScreenBinding

class SpashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySpashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        normalLaunch()
    }


    private fun normalLaunch() {
        Handler().postDelayed({
            startActivity(Intent(this@SpashScreen, LogIn::class.java))
            finish()
        }, 3000)
    }
}