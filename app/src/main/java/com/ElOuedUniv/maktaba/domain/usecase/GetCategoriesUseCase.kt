package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.model.Category
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository

// TODO: Implement this use case
class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {
   operator fun invoke(): List<Category> {
    return categoryRepository.getAllCategories()
}
}
