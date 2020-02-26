package com.example.task2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ItemClass::class],version = 1, exportSchema = false)
abstract class ItemDB: RoomDatabase()
{
    abstract fun itemDAO(): ItemDAO

    companion object{
        @Volatile
        private var INSTANCE : ItemDB?=null

        fun getAppDatabase(context:Context, scope: CoroutineScope): ItemDB
        {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDB::class.java,
                    "itemDB"
                )
                    .addCallback(ItemDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        private class ItemDatabaseCallback(private val scope: CoroutineScope):RoomDatabase.Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch{
                        populateDatabase(database.itemDAO())
                    }
                }
            }
        }

        suspend fun populateDatabase(itemDAO: ItemDAO){
            itemDAO.deleteAll()

            var item = ItemClass("Test 1", "It is Ready to Run\nLets Cheers!",
                "My name is Isabella. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://ichef.bbci.co.uk/news/320/cpsprodpb/8398/production/_103888633_hi016427699.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 2", "It is Ready to Run\nLets Cheers!",
                "My name is Miki. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://mk0healthybutsm3jfyo.kinstacdn.com/wp-content/uploads/2018/08/www.maxpixel.net-Plant-Nature-Flower-Dandelion-3236270-min.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 3", "It is Ready to Run\nLets Cheers!",
                "My name is HooiXin. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.twentyonepilots.com/sites/g/files/g2000004896/f/sample001.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 4", "It is Ready to Run\nLets Cheers!",
                "My name is TuTu. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://miro.medium.com/max/1500/1*MI686k5sDQrISBM6L8pf5A.jpeg")
            itemDAO.insert(item)

            item = ItemClass("Test 5", "It is Ready to Run\nLets Cheers!",
                "My name is Rou. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.twentyonepilots.com/sites/g/files/g2000004896/f/Sample-image10-highres.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 6", "It is Ready to Run\nLets Cheers!",
                "My name is NaNa. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.visioncritical.com/hubfs/Imported_Blog_Media/BLG_Andrew-G_-River-Sample_09_13_12.png")
            itemDAO.insert(item)

            item = ItemClass("Test 7", "It is Ready to Run\nLets Cheers!",
                "My name is Shirley. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.twentyonepilots.com/sites/g/files/g2000004896/f/sample_07_0.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 8", "It is Ready to Run\nLets Cheers!",
                "My name is XiaoNan. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.twentyonepilots.com/sites/g/files/g2000004896/f/hr10_sample_image_02_0.jpg")
            itemDAO.insert(item)

            item = ItemClass("Test 9", "It is Ready to Run\nLets Cheers!",
                "My name is Cindy. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.fujifilm.com/products/digital_cameras/x/fujifilm_x_t3/sample_images/img/index/ff_x_t3_001.JPG")
            itemDAO.insert(item)

            item = ItemClass("Test 10", "It is Ready to Run\nLets Cheers!",
                "My name is Bobo. \nI always wake up at 7 o’clock. \nIn the morning, I wash my face and brush my teeth. \nAfter that,  I do some exercises then I put my clothes on and prepare my school bag. \nNext, I have my breakfast and wait for the school bus. \nAt 8 o’clock I go to school and start my first class then I go to the second class. \nAfter that, I often take my lunch break and talk with my friends. \nAt around 12 noon I go back home and take a rest. \nNext, I usually watch some TV and chat with my family then I do my homework and help my mother in the house. \nAt  9 o’clock I read a book and go to sleep.",
                "https://www.fujifilm.com/products/digital_cameras/x/fujifilm_x_t3/sample_images/img/index/ff_x_t3_002.JPG")
            itemDAO.insert(item)
        }

    }

}