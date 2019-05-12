package com.example.lexshop.admin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.lexshop.R
import com.example.lexshop.registerLogin.LoginActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_admin_category.*

class AdminCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_category)
        Paper.init(this)

        t_shirt.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","t_shirt")
            startActivity(i)
        }

        sports_t_shirt.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","sports_t_shirts")
            startActivity(i)
        }

        female_dresses.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","female_dresses")
            startActivity(i)
        }

        sweaters.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","sweaters")
            startActivity(i)
        }

        glasses.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","glasses")
            startActivity(i)
        }

        purses_bags_wallets.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","purses_bags_wallets")
            startActivity(i)
        }

        hats.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","hats")
            startActivity(i)
        }

        shoes.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","shoes")
            startActivity(i)
        }

        headphones.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","headphones")
            startActivity(i)
        }

        laptops.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","laptops")
            startActivity(i)
        }

        watches.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","watches")
            startActivity(i)
        }

        mobile.setOnClickListener {
            val i = Intent(this,AdminActivity::class.java)
            i.putExtra("category","mobile")
            startActivity(i)
        }

        back.setOnClickListener {
            Paper.book().destroy()
            val i  = Intent(this,LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
    }
}
