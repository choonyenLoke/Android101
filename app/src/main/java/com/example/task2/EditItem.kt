package com.example.task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso

class EditItem : AppCompatActivity() {

    private lateinit var titleView: EditText
    private lateinit var subView: EditText
    private lateinit var descView: EditText
    private lateinit var urlView: EditText
    private lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        val actionbar = supportActionBar
        actionbar?.title = "Update Item Detail"

        intent?.let {

            titleView = findViewById(R.id.update_title)
            subView = findViewById(R.id.update_sub)
            descView = findViewById(R.id.update_desc)
            urlView = findViewById(R.id.update_url)
            imgView = findViewById(R.id.imgPreviewUpdate)

            val title = it.getStringExtra("Title")
            val sub = it.getStringExtra("Sub")
            val desc = it.getStringExtra("Description")
            val imgUrl = it.getStringExtra("url")

            titleView.setText(title)
            subView.setText(sub)
            descView.setText(desc)
            urlView.setText(imgUrl)
            Picasso.with(this)
                .load(imgUrl)
                .error(R.drawable.broken)
                .placeholder(R.drawable.load)
                .resize(200,200)
                .centerCrop()
                .into(imgView)
        }
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener{
            var replyIntent = Intent()
            if(TextUtils.isEmpty(titleView.text) || (TextUtils.isEmpty(subView.text))
                || (TextUtils.isEmpty(descView.text)) || (TextUtils.isEmpty(urlView.text)))
            {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else
            {
                val updateTitle = titleView.text.toString()
                val updateSub = subView.text.toString()
                val updateDesc = descView.text.toString()
                val updateUrl = urlView.text.toString()

                replyIntent.putExtra("UpdateTitle", updateTitle)
                replyIntent.putExtra("UpdateSub", updateSub)
                replyIntent.putExtra("UpdateDesc", updateDesc)
                replyIntent.putExtra("UpdateUrl", updateUrl)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        urlView.setOnFocusChangeListener { _, hasFocus -> if(!hasFocus){ loadImage(urlView.text.toString()) } }
    }

    fun loadImage(url:String){
        if(url == ""){
            Toast.makeText(this, "Image Url is empty", Toast.LENGTH_LONG).show()
        }
        else
        {
            Picasso.with(this)
                .load(url)
                .resize(200,200)
                .centerCrop()
                .error(R.drawable.broken)
                .placeholder(R.drawable.load)
                .into(imgView)
        }
    }
}
