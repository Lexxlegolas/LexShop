package com.example.lexshop.registerLogin

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lexshop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_regiser.*
import java.util.HashMap

class RegiserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var loadingBar:ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regiser)
        loadingBar= ProgressDialog(this)

        register_register_btn.setOnClickListener {
            register()
        }
    }

    private fun register()
    {
        auth = FirebaseAuth.getInstance()
        val name = username_register.text.toString()
        val number = number_register.text.toString()
        val password = password_regiser.text.toString()

        if (name.isEmpty() || number.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Make sure all fields are entered",Toast.LENGTH_SHORT).show()
        }
        else
        {
            loadingBar!!.setTitle("Creating Account")
            loadingBar!!.setMessage("Please wait while we are checking your credentials")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()

            validatePhoneNumber(name,number,password)
        }


    }

    private fun validatePhoneNumber(name:String,number:String,password:String)
    {
        val ref = FirebaseDatabase.getInstance().getReference()
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (!(p0.child("Users").child(number).exists()))
                {
                    val userdataMap = HashMap<String, Any>()
                    userdataMap["phone"] = number
                    userdataMap["password"] = password
                    userdataMap["name"] = name

                    ref.child("Users").child(number).updateChildren(userdataMap)
                        .addOnCompleteListener {
                            if (it.isSuccessful)
                            {
                                Toast.makeText(this@RegiserActivity,"Congratulations your account has been created",Toast.LENGTH_SHORT).show()
                                loadingBar!!.dismiss()

                                var i = Intent(this@RegiserActivity,LoginActivity::class.java)
                                startActivity(i)
                            }
                            else
                            {
                                loadingBar!!.dismiss()
                                Toast.makeText(this@RegiserActivity, "Network error pls try again", Toast.LENGTH_SHORT).show()

                                val i = Intent(this@RegiserActivity, RegiserActivity::class.java)
                                startActivity(i)
                            }
                        }
                }else
                {
                    Toast.makeText(this@RegiserActivity,"This $number already exists",Toast.LENGTH_LONG).show()
                    loadingBar!!.dismiss()

                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
