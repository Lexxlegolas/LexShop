package com.example.lexshop

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.lexshop.home.HomeActivity
import com.example.lexshop.model.Users
import com.example.lexshop.prevalent.Prevalent
import com.example.lexshop.registerLogin.LoginActivity
import com.example.lexshop.registerLogin.RegiserActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object
    {
        val prevalent:Prevalent? = null
        var parentDbName = "Users"
        var user :Users? = null

    }
    private var loadingBar : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Paper.init(this)
        loadingBar = ProgressDialog(this)

        login_btn.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        register.setOnClickListener {
            val i = Intent(this, RegiserActivity::class.java)
            startActivity(i)
        }

        /* val PhoneKey = Paper.book().read<String>(prevalent!!.userPhoneKey)
        val PasswordKey = Paper.book().read<String>(prevalent!!.userPasswordKey)

        if (PhoneKey != "" && PasswordKey !="" )
        {
            if (!TextUtils.isEmpty(PhoneKey) && !TextUtils.isEmpty(PhoneKey))
            {
                AllowAccess(PhoneKey,PasswordKey)
                loadingBar!!.setTitle("Already Login in")
                loadingBar!!.setMessage("Just a moment")
                loadingBar!!.setCanceledOnTouchOutside(false)
                loadingBar!!.show()
            }
        }
    }*/

        /*private fun AllowAccess(number: String, password: String)
    {
        val ref= FirebaseDatabase.getInstance().getReference()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.child(parentDbName).child(number).exists())
                {
                    LoginActivity.user = p0.child(parentDbName).child(number).getValue(Users::class.java)
                    //user =p0.getValue(Users::class.java)
                    if (user?.phone == number)
                    {
                        if (user?.password == password)
                        {
                            Toast.makeText(this@MainActivity,"Logged in Successfully", Toast.LENGTH_SHORT).show()
                            loadingBar!!.dismiss()

                            val i = Intent(this@MainActivity, HomeActivity::class.java)
                            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(i)
                        }
                        else
                        {
                            Toast.makeText(this@MainActivity,"Password is incorrect", Toast.LENGTH_SHORT).show()
                            loadingBar!!.dismiss()
                        }

                    }
                }
                else
                {
                    Toast.makeText(this@MainActivity,"Account with this $number does not exist", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }*/
    }
}
