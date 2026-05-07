package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupabaseCategoryRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = flow {
        val categories = supabaseClient.postgrest["categories"]
            .select()
            .decodeList<Category>()
        emit(categories)
    }

    override suspend fun getCategoryById(id: String): Category? {
        return supabaseClient.postgrest["categories"]
            .select {
                filter {
                    eq("id", id)
                }
            }
            .decodeSingleOrNull<Category>()
    }
}
