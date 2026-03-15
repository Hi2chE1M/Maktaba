package com.ElOuedUniv.maktaba.data.repository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepositoryImpl
import com.ElOuedUniv.maktaba.data.repository.BookRepositoryImpl

import com.ElOuedUniv.maktaba.data.model.Category

class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
    Category(
        id = "1",
        name = "Programming",
        description = "Books about software development and coding"
    ),
    Category(
        id = "2",
        name = "Algorithms",
        description = "Books about algorithms and data structures"
    ),
    Category(
        id = "3",
        name = "Databases",
        description = "Books about database design and management"
    ),
      Category(
        id = "4",
        name = "Mobile Development",
        description = "Books about Android and iOS development"
    ),
      Category(
        id = "5",
        name = "Web Development",
        description = "Books about front-end and back-end developmen"
    )
)

 override fun getAllCategories(): List<Category> {
        return categoriesList
    }

    override fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}
