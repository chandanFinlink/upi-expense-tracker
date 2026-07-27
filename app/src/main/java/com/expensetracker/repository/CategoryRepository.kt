package com.expensetracker.repository

import com.expensetracker.database.dao.CategoryDao
import com.expensetracker.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow


class CategoryRepository(
    private val categoryDao: CategoryDao
) {


    fun getCategories(): Flow<List<CategoryEntity>> {

        return categoryDao.getCategories()

    }


    suspend fun insertCategory(
        category: CategoryEntity
    ): Long {

        return categoryDao.insertCategory(category)

    }


    suspend fun insertDefaultCategories(
        categories: List<CategoryEntity>
    ) {

        categoryDao.insertCategories(categories)

    }


    suspend fun getCategoryById(
        id: Long
    ): CategoryEntity? {

        return categoryDao.getCategoryById(id)

    }

}