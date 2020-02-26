package com.example.task2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ItemRepository

    val allItems: LiveData<List<ItemClass>>

    init {
        val itemDAO = ItemDB.getAppDatabase(application, viewModelScope).itemDAO()
        repository = ItemRepository(itemDAO)
        allItems = repository.allItems
    }

    fun insert(item: ItemClass) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ItemClass) = viewModelScope.launch {
        repository.update(item)
    }
}