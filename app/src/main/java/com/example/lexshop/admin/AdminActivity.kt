package com.example.lexshop.admin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.lexshop.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_admin.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AdminActivity : AppCompatActivity() {


    private var ImageUri: Uri? = null
    private var saveCurrentDate: String? = null
    private var saveCurrentTime: String? = null
    private var productRandomKey: String? = null
    private var downloadImageUrl: String? = null
    private var productImageRef: StorageReference? = null
    private var productRef: DatabaseReference? = null
    private var Pname: String? = null
    private var Description: String? = null
    private var categoryName: String? = null
    private var Price: String? = null
    private var loadingBar : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        categoryName = intent.extras.get("category").toString()
        productImageRef = FirebaseStorage.getInstance().reference.child("Product Image")
        productRef = FirebaseDatabase.getInstance().reference.child("Products")
        loadingBar = ProgressDialog(this)

        image_product.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i, 0)
        }

        add_new_product.setOnClickListener {
            validateProduct()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            ImageUri = data.data
            image_product.setImageURI(ImageUri)
        }
    }

    private fun validateProduct() {
        Pname = product_name.text.toString()
        Description = product_description.text.toString()
        Price = product_price.text.toString()

        if (ImageUri == null) {
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show()
        } else if (Pname!!.isEmpty() || Description!!.isEmpty() || Price!!.isEmpty()) {
            Toast.makeText(this, "make sure all fields are entered", Toast.LENGTH_SHORT).show()
        } else {
            storeProductInfo()
        }
    }

    private fun storeProductInfo() {
        loadingBar!!.setTitle("Adding new Product")
        loadingBar!!.setMessage("Just a moment")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()

        val calender = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MMM dd,yyyy")
        saveCurrentDate = currentDate.format(calender.time)


        val currentTime = SimpleDateFormat("HH:MM:SS a")
        saveCurrentTime = currentTime.format(calender.time)

        productRandomKey = saveCurrentDate + saveCurrentTime

        val filePath =productImageRef!!.child(ImageUri!!.lastPathSegment + productRandomKey + ".jpg")
        val uploadTask =filePath.putFile(ImageUri!!)

        uploadTask.addOnFailureListener {e->
            val message =e.toString()
            Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
            loadingBar!!.dismiss()
        }
            .addOnSuccessListener {
                Toast.makeText(this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show()

                val uriTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.getException()!!
                    }

                    downloadImageUrl = filePath.downloadUrl.toString()
                    filePath.downloadUrl
                }
                    .addOnCompleteListener { task ->
                        downloadImageUrl = task.result.toString()
                        Toast.makeText(this, "got product uri successfully", Toast.LENGTH_SHORT).show()
                        saveProductInfoToDatabase()
                    }
            }

    }

    private fun saveProductInfoToDatabase()
    {
        val prodactMap = HashMap<String, Any>()
        prodactMap["pid"] = productRandomKey!!
        prodactMap["date"] = saveCurrentDate!!
        prodactMap["time"] = saveCurrentTime!!
        prodactMap["category"] = categoryName!!
        prodactMap["description"] = Description!!
        prodactMap["price"] = Price!!
        prodactMap["image"] = downloadImageUrl!!
        prodactMap["pname"] = Pname!!

        productRef!!.child(productRandomKey!!).updateChildren(prodactMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val i = Intent(this,AdminCategoryActivity::class.java)
                    startActivity(i)

                    loadingBar!!.dismiss()
                    Toast.makeText(this, " product uploaded successfully", Toast.LENGTH_SHORT).show()
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }
    }
}





