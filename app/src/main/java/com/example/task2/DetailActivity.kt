package com.example.task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

private const val itemActivityRequestCode = 1

class DetailActivity : AppCompatActivity() {

    private var url: String? = ""
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionbar = supportActionBar
        actionbar?.title = "Item Details"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        intent?.let {

            val title = it.getStringExtra("Title")
            val sub = it.getStringExtra("Sub")
            val desc = it.getStringExtra("Description")
            val imgUrl = it.getStringExtra("Url")
            url = imgUrl

            val detailTitle = imageTitle
            detailTitle.text = title
            val detailSub = imageSubTitle
            detailSub.text = sub
            val detailDesc = imageDescription
            detailDesc.text = desc
            val detailImg = detailImage
            Picasso.with(this)
                .load(imgUrl)
                .error(R.drawable.broken)
                .centerCrop()
                .resize(300,300)
                .placeholder(R.drawable.load)
                .into(detailImg)
        }
        val fabEdit = fab_edit
        val detailTitle = imageTitle
        val detailSub = imageSubTitle
        val detailDesc = imageDescription

        fabEdit.setOnClickListener{
            val intent = Intent(this, EditItemActivity::class.java)
            intent.putExtra("Title", detailTitle.text.toString())
            intent.putExtra("Sub", detailSub.text.toString())
            intent.putExtra("Description", detailDesc.text.toString())
            intent.putExtra("url", url)

            startActivityForResult(intent, itemActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var updateTitle = ""
        var updateSub = ""
        var updateDesc = ""
        var updateUrl = ""

        if(requestCode == itemActivityRequestCode && resultCode == Activity.RESULT_OK)
        {

            data?.let {
                updateTitle = it.getStringExtra("UpdateTitle").orEmpty()
                updateSub = it.getStringExtra("UpdateSub").orEmpty()
                updateDesc = it.getStringExtra("UpdateDesc").orEmpty()
                updateUrl = it.getStringExtra("UpdateUrl").orEmpty()
            }

            val item = ItemClass(updateTitle, updateSub, updateDesc, updateUrl)

            val title = imageTitle
            val sub = imageSubTitle
            val desc = imageDescription
            val imgView = detailImage

            title.text = updateTitle
            sub.text = updateSub
            desc.text = updateDesc
            Picasso.with(this)
                .load(updateUrl)
                .error(R.drawable.broken)
                .placeholder(R.drawable.load)
                .centerCrop()
                .resize(200,200)
                .into(imgView)

            itemViewModel.update(item)
            Toast.makeText(
                applicationContext,
                "Item Updated",
                Toast.LENGTH_LONG
            ).show()
        }
        else
        {
            Toast.makeText(
                applicationContext,
                "Item not updated since there is empty field",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
