package com.example.lexshop.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lexshop.MainActivity
import com.example.lexshop.R
import com.example.lexshop.registerLogin.RegiserActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Logout.setOnClickListener {
            Paper.book().destroy()
            val i = Intent(this,MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
        }
    }
}
