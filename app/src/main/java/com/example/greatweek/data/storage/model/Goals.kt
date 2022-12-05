package com.example.greatweek.data.storage.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Roles::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("role_id"),
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Goals(
    @PrimaryKey val id: Int,
    @NonNull val title: String,
    val description: String,
    @ColumnInfo(name = "role_id") val roleId: String,
    val day: Int,
    val type: String
)