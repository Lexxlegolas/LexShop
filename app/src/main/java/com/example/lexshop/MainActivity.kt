package com.example.lexshop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lexshop.registerLogin.LoginActivity
import com.example.lexshop.registerLogin.RegiserActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_btn.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }

        register.setOnClickListener {
            val i = Intent(this,RegiserActivity::class.java)
            startActivity(i)
        }
    }
}
