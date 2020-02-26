package com.example.task2

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDAO: ItemDAO) {

    val allItems: LiveData<List<ItemClass>> = itemDAO.getItemList()

    suspend fun insert(item: ItemClass){
        itemDAO.insert(item)
    }
    suspend fun update(item: ItemClass){
        itemDAO.updateData(item)
    }
}

