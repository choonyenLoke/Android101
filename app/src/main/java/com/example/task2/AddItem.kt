package com.example.task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class AddItem : AppCompatActivity() {

    private lateinit var editTitle: EditText
    private lateinit var editSub: EditText
    private lateinit var editDesc: EditText
    private lateinit var editUrl: EditText
    private lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val actionbar = supportActionBar
        actionbar?.title = "Add New Item"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        editTitle = findViewById(R.id.edit_title)
        editSub = findViewById(R.id.edit_sub)
        editDesc = findViewById(R.id.edit_desc)
        editUrl = findViewById(R.id.edit_url)

        val button = findViewById<Button>(R.id.btnSave)
        button.setOnClickListener{
            var replyIntent = Intent()
            if(TextUtils.isEmpty(editTitle.text) || (TextUtils.isEmpty(editSub.text))
                || (TextUtils.isEmpty(editDesc.text)) || (TextUtils.isEmpty(editUrl.text)))
            {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            else
            {
                val title = editTitle.text.toString()
                val subTitle = editSub.text.toString()
                val desc = editDesc.text.toString()
                val url = editUrl.text.toString()

                replyIntent.putExtra(EXTRA_TITLE, title)
                replyIntent.putExtra(EXTRA_SUB, subTitle)
                replyIntent.putExtra(EXTRA_DESC, desc)
                replyIntent.putExtra(EXTRA_IMG, url)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        editUrl.setOnFocusChangeListener { _, hasFocus -> if(!hasFocus) loadPreviewImage(editUrl.text.toString()) }
    }
    companion object {
        const val EXTRA_TITLE = "com.example.android.wordlistsql.TITLE"
        const val EXTRA_SUB = "com.example.android.wordlistsql.SUB"
        const val EXTRA_DESC = "com.example.android.wordlistsql.DESC"
        const val EXTRA_IMG = "com.example.android.wordlistsql.IMG"
    }

    fun loadPreviewImage(url:String){
        imgView = findViewById(R.id.imgPreview)
        if(url == "")
        {
            Toast.makeText(this, "Image Url is empty",Toast.LENGTH_LONG).show()
        }
        else{
            Picasso.with(this)
                .load(url)
                .resize(100,100)
                .centerCrop()
                .placeholder(R.drawable.load)
                .error(R.drawable.broken)
                .into(imgView)
        }
    }
}
