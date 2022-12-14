package com.example.greatweek.data.storage

import androidx.room.*
import com.example.greatweek.data.storage.model.Roles
import kotlinx.coroutines.flow.Flow

@Dao
interface RoleDao {

    @Query("SELECT * FROM roles")
    fun getRoles(): Flow<List<Roles>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRole(role: Roles)

    @Query("DELETE FROM roles WHERE name = :name")
    suspend fun deleteRole(name: String)

    @Query("UPDATE roles SET name = :newName WHERE name = :oldName")
    suspend fun updateRole(oldName: String, newName: String)

    @Query("SELECT * FROM roles WHERE name = :name")
    suspend fun getRole(name: String): Roles
}