package com.example.kotlin_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog
import android.widget.Button;
import kotlinx.android.synthetic.main.activity_main.*

import android.view.View;
import android.view.Menu;
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    companion object{
        private val PICK_IMAGE_CODE=1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_CODE) {

            alertDialog.show()
            val uploadTask = storageReference!!.putFile(data!!.data!!)
            val task = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl

            }.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val downloadUri=task.result
                    val url =downloadUri!!.toString()
                    Log.d("DIRECTLINK",url)
                    alertDialog.dismiss()
                }

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //alertDialog=SpotsDialog.Builder().setContext(this).build();
        storageReference=FirebaseStorage.getInstance().getReference("image_view");
//       btn = (Button)findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener{
            val intent=Intent()
            intent.type="image/*"
            startActivityForResult(Intent.createChooser(intent,"Select pictures"),PICK_IMAGE_CODE)

        }

    }
}