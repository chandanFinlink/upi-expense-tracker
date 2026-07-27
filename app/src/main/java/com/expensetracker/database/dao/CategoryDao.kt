package com.expensetracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.expensetracker.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {


    @Insert
    suspend fun insertCategory(
        category: CategoryEntity
    ): Long


    @Insert
    suspend fun insertCategories(
        categories: List<CategoryEntity>
    )


    @Query(
        """
        SELECT *
        FROM categories
        ORDER BY name ASC
        """
    )
    fun getCategories(): Flow<List<CategoryEntity>>


    @Query(
        """
        SELECT *
        FROM categories
        WHERE id = :id
        """
    )
    suspend fun getCategoryById(
        id: Long
    ): CategoryEntity?

}