package com.example.task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_item.*
import org.w3c.dom.Text

class AddEditItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val actionbar = supportActionBar

        actionbar?.setDisplayHomeAsUpEnabled(true)

        val editTitle = edit_title
        val editSub = edit_sub
        val editDesc = edit_desc
        val editUrl = edit_url
        val imgView = imgPreview

        fun addOrUpdate(){
            val button = btnSave
            button.setOnClickListener{
                val replyIntent = Intent()
                if(TextUtils.isEmpty(editTitle.text) || TextUtils.isEmpty(editSub.text)
                    || TextUtils.isEmpty(editDesc.text) || TextUtils.isEmpty(editUrl.text)
                )
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
        }

        val activityClass = intent?.getStringExtra("ActivityName")

        if(activityClass.equals("DetailItem")){
            actionbar?.title = "Update Item Detail"
            btnSave.text = getString(R.string.btnUpdate)

            intent?.let {

                val title = it.getStringExtra("Title")
                val sub = it.getStringExtra("Sub")
                val desc = it.getStringExtra("Description")
                val imgUrl = it.getStringExtra("url")

                editTitle.setText(title)
                editSub.setText(sub)
                editDesc.setText(desc)
                editUrl.setText(imgUrl)
                Picasso.with(this)
                    .load(imgUrl)
                    .error(R.drawable.broken)
                    .placeholder(R.drawable.load)
                    .resize(200,200)
                    .centerCrop()
                    .into(imgView)
                editTitle.isEnabled = false

            }
            addOrUpdate()
        }
        else if(activityClass.equals("ListItem"))
        {
            actionbar?.title = "Add New Item"
            btnSave.text = getString(R.string.btnSave)
            addOrUpdate()
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
        val imgView = imgPreview
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
