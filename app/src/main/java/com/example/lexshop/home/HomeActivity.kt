package com.example.lexshop.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.example.lexshop.MainActivity
import com.example.lexshop.R
import com.example.lexshop.model.Products
import com.example.lexshop.model.Users
import com.example.lexshop.prevalent.Prev
import com.example.lexshop.prevalent.Prevalent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import io.paperdb.Paper
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.products_items_layout.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object{
        var prevalent: Prevalent? = null
        val prev: Prev? = null

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Paper.init(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title="Home"
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val headerView:View = navView.getHeaderView(0)
        val usernameTextView:TextView = headerView.findViewById(R.id.user_profile_name)
        val profilImageView:CircleImageView = headerView.findViewById(R.id.user_profile_image)

        usernameTextView.setText(prev?.currentOnlieUsers?.name)

        fetchProducts()

    }

    class ProductItems(val products:Products):Item<ViewHolder>()
    {
        override fun bind(viewHolder: ViewHolder, position: Int)
        {
            viewHolder.itemView.Product_Name.text = products.pname
            Picasso.get().load(products.image).into(viewHolder.itemView.Product_Image)
            viewHolder.itemView.Product_Description.text = products.description
            viewHolder.itemView.Product_Price.text = products.price
        }
        override fun getLayout(): Int
        {
            return R.layout.products_items_layout
        }
    }

    private fun fetchProducts()
    {
        val ref = FirebaseDatabase.getInstance().getReference().child("Products")
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot)
            {
                val adapter = GroupAdapter<ViewHolder>()

                p0.children.forEach {
                    val products = it.getValue(Products::class.java)
                    if (products != null)
                    {
                        adapter.add(ProductItems(products))
                    }

                }

                recycler_menu.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_cart ->
            {

            }
            R.id.nav_orders ->
            {

            }
            R.id.nav_categories ->
            {

            }
            R.id.nav_settings ->
            {

            }
            R.id.nav_logout ->
            {
                Paper.book().destroy()
                val i = Intent(this,MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
