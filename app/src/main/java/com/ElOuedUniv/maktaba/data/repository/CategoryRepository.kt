package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    
    fun getAllCategories(): Flow<List<Category>>
    
    suspend fun getCategoryById(id: String): Category?
}
