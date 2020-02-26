package com.example.task2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDAO
{
    @Insert
    suspend fun insert(itemClass: ItemClass)

    @Query(value = "Select * From item_table")
    fun getItemList():LiveData<List<ItemClass>>

    @Query(value = "Delete From item_table")
    suspend fun deleteAll()

    @Update
    suspend fun updateData(itemClass: ItemClass)
}