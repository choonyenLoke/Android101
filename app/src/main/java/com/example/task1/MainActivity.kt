package com.example.task1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view.*
import org.w3c.dom.Text
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var itemList: ArrayList<Item>? = null


    companion object {
        const val ITEM = "item"
    }

    private fun loadData(){
        val titles = arrayOf("Test 1", "Test 2", "Test 3",
            "Test 4", "Test 5", "Test 6", "Test 7", "Test 8", "Test 9",
            "Test 10", "Test 11", "Test 12", "Test 13", "Test 14", "Test 15",
            "Test 16", "Test 17", "Test 18", "Test 19", "Test 20")

        val subTitles = arrayOf("Test 1 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 2 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 3 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 4 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 5 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 6 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 7 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 8 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 9 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 10 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 11 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 12 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 13 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 14 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 15 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 16 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 17 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 18 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 19 is completely successful created\n Please Continue\nJust Do It!!!!",
            "Test 20 is completely successful created\n Please Continue\nJust Do It!!!!")

        val images = intArrayOf(R.drawable.test_1, R.drawable.test_2, R.drawable.test_3,
            R.drawable.test_4, R.drawable.test_5, R.drawable.test_6, R.drawable.test_7, R.drawable.test_8,
            R.drawable.test_9, R.drawable.test_10, R.drawable.test_11, R.drawable.test_12, R.drawable.test_13,
            R.drawable.test_14, R.drawable.test_15, R.drawable.test_16, R.drawable.test_17, R.drawable.test_18,
            R.drawable.test_19, R.drawable.test_20)

        val desc = arrayOf("My name is Adam. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep." ,
            "My name is Michelle. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is John. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Alex. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Jayce. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Ivan. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Tim. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Koo. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Bell. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Lucas. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Macy. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep." ,
            "My name is Isabella. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Miki. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Marco. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Susan. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Serene. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Carmen. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is James. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Gem. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
            "My name is Joker. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.")

        for(i in 0..19){
            val item = Item(images[i],titles[i], subTitles[i], desc[i])
            itemList!!.add(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = ArrayList<Item>()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        adapter = RecyclerAdapter(itemList!!)
        recyclerView.adapter = adapter

        loadData()
        recyclerView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v != null) {
            val itemImage = v.findViewById<ImageView>(R.id.item_img)
            val itemTitle = v.findViewById<TextView>(R.id.item_title)
            val itemSubTitle = v.findViewById<TextView>(R.id.subTitle)
            val itemDesc = v.findViewById<TextView>(R.id.item_desc)

            val title = itemTitle.text.toString()
            val subTitle = itemSubTitle.text.toString()
            val desc = itemDesc.text.toString()
            val imageRes = itemImage.tag.toString().toInt()

            val item = Item(imageRes, title, subTitle, desc)

            val intent = Intent(v.context, DetailActivity::class.java)
            intent.putExtra(ITEM, item)

            v.context.startActivity(intent)
        }
    }

}
