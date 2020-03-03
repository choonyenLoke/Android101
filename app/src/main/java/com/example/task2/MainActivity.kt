package com.example.task2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val itemActivityRequestCode = 1

class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.title = "List Item"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter:ItemAdapter = ItemAdapter(this)
        adapter.onItemClick ={
            itemClass ->
            //Toast.makeText(this@MainActivity,itemClass.itemIitle, Toast.LENGTH_LONG).show()
            val imgUrl = itemClass.itemUrl
            val title = itemClass.itemTitle
            val sub = itemClass.itemSub
            val desc = itemClass.itemDesc

            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("Title", title)
            intent.putExtra("Sub", sub)
            intent.putExtra("Description", desc)
            intent.putExtra("Url", imgUrl)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemViewModel.allItems.observe(this, Observer { item ->
            item?.let { adapter.setItem(it) }
        })



        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val intent = Intent (this, AddEditItemActivity::class.java)
            intent.putExtra("ActivityName", "ListItem")
            startActivityForResult(intent, itemActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var title = ""
        var sub = ""
        var desc = ""
        var imgUrl = ""

        if(requestCode == itemActivityRequestCode && resultCode == Activity.RESULT_OK){

            data?.let{
                title = it.getStringExtra(AddEditItemActivity.EXTRA_TITLE).orEmpty()
                sub = it.getStringExtra(AddEditItemActivity.EXTRA_SUB).orEmpty()
                desc = it.getStringExtra(AddEditItemActivity.EXTRA_DESC).orEmpty()
                imgUrl = it.getStringExtra(AddEditItemActivity.EXTRA_IMG).orEmpty()
            }

            val item = ItemClass(title,sub,desc,imgUrl)
            itemViewModel.insert(item)
            Toast.makeText(
                applicationContext,
                "Item Saved",
                Toast.LENGTH_LONG
            ).show()

        }
        else
        {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
