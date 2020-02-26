package com.example.task2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemClass(
    @PrimaryKey
    @ColumnInfo(name="Title")
    var itemTitle: String,
    @ColumnInfo(name="SubTitle")
    var itemSub: String,
    @ColumnInfo(name="Desc")
    var itemDesc: String,
    @ColumnInfo(name="ImageUrl")
    var itemUrl: String


)