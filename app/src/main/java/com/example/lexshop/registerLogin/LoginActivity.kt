package com.example.lexshop.registerLogin

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lexshop.R
import com.example.lexshop.admin.AdminActivity
import com.example.lexshop.admin.AdminCategoryActivity
import com.example.lexshop.home.HomeActivity
import com.example.lexshop.model.Users
import com.example.lexshop.prevalent.Prevalent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var loadingBar :ProgressDialog? = null

    companion object{
        var parentDbName = "Users"
        var user :Users? = null
        var prevalent:Prevalent? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingBar = ProgressDialog(this)
        Paper.init(this)

        login_login_btn.setOnClickListener {
            login()
        }

        admin_link.setOnClickListener {
            login_login_btn.text="Login Admin"
            admin_link.visibility =View.INVISIBLE
            not_admin_link.visibility =View.VISIBLE
            parentDbName = "Admin"
        }

        not_admin_link.setOnClickListener {
            login_login_btn.text="Login"
            admin_link.visibility =View.VISIBLE
            not_admin_link.visibility =View.INVISIBLE
            parentDbName = "Users"
        }
    }

    private fun login()
    {
        val number = login_number.text.toString()
        val password = login_password.text.toString()

        if (number.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Make sure all fields are entered",Toast.LENGTH_SHORT).show()
        }
        else
        {
            loadingBar!!.setTitle("Login in")
            loadingBar!!.setMessage("Just a moment")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()

            allowAccess(number,password)

        }
    }

    private fun allowAccess(number:String,password:String)
    {

        if(remember_me_chk.isChecked)
        {
            Paper.book().write(prevalent!!.userPhoneKey,number)
            Paper.book().write(prevalent!!.userPasswordKey,password)
        }

        val ref= FirebaseDatabase.getInstance().getReference()
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.child(parentDbName).child(number).exists())
                {
                    user = p0.child(parentDbName).child(number).getValue(Users::class.java)
                    //user =p0.getValue(Users::class.java)
                    if (user?.phone == number)
                    {
                        if (user?.password == password)
                        {
                           if (parentDbName.equals("Admin"))
                           {
                               Toast.makeText(this@LoginActivity,"Welcome Admin You Logged in Successfully",Toast.LENGTH_SHORT).show()
                               loadingBar!!.dismiss()

                               val i = Intent(this@LoginActivity, AdminCategoryActivity::class.java)
                               i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                               startActivity(i)
                           }
                            else if (parentDbName.equals("Users"))
                           {
                               Toast.makeText(this@LoginActivity,"Logged in Successfully",Toast.LENGTH_SHORT).show()
                               loadingBar!!.dismiss()

                               val i = Intent(this@LoginActivity,HomeActivity::class.java)
                               i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                               startActivity(i)
                           }
                        }
                        else
                        {
                            Toast.makeText(this@LoginActivity,"Password is incorrect",Toast.LENGTH_SHORT).show()
                            loadingBar!!.dismiss()
                        }

                    }
                }
                else
                {
                    Toast.makeText(this@LoginActivity,"Account with this $number does not exist",Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
