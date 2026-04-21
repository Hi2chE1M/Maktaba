# TP2: Implementing Category Management with MVVM

**Course:** Mobile Application Development  
**University:** El Oued University (جامعة الوادي)  
**Level:** 3rd Year Computer Science  
**Duration:** 3 hours  
**Topic:** Extending MVVM Architecture with Category Feature

---

## 🎯 Learning Objectives

By the end of this practical session, you will be able to:

1. ✅ Create **data models** with proper attributes
2. ✅ Implement **repository interfaces** and their implementations
3. ✅ Build **use cases** to encapsulate business logic
4. ✅ Connect **ViewModels** to use cases
5. ✅ Update **UI views** to display dynamic data
6. ✅ Apply the **Repository Pattern** with interfaces
7. ✅ Navigate between screens in Jetpack Compose

---

## 📚 Theory: Repository Pattern with Interfaces

### Why Use Interfaces?

In TP1, we worked with `BookRepository` as a concrete class. Now, we've refactored it to use an **interface-based approach**:

```
┌─────────────────────────────────────┐
│   BookRepository (Interface)        │
│   • getAllBooks()                   │
│   • getBookByIsbn(isbn)             │
└─────────────────┬───────────────────┘
                  │ implements
                  ↓
┌─────────────────────────────────────┐
│   BookRepositoryImpl (Class)        │
│   • Actual implementation           │
│   • Contains the book list          │
└─────────────────────────────────────┘
```

**Benefits:**
- ✅ **Abstraction**: Separate contract from implementation
- ✅ **Testability**: Easy to create mock implementations
- ✅ **Flexibility**: Swap implementations (e.g., in-memory → database)
- ✅ **SOLID Principles**: Follows Dependency Inversion Principle

---

## 📂 What's Already Done

Before starting the exercises, let's review what has been prepared for you:

### 1. Book Repository Refactoring ✅

The `BookRepository` has been split into:
- **`BookRepository.kt`** (interface) - Defines the contract
- **`BookRepositoryImpl.kt`** (implementation) - Contains the actual data

### 2. Category Infrastructure (Stubs) ✅

The following files have been created with TODO placeholders:

```
app/src/main/java/com/ElOuedUniv/maktaba/
├── data/
│   ├── model/
│   │   └── Category.kt                    # ⚠️ TODO: Add attributes
│   └── repository/
│       ├── CategoryRepository.kt          # ✅ Interface ready
│       └── CategoryRepositoryImpl.kt      # ⚠️ TODO: Implement
├── domain/
│   └── usecase/
│       └── GetCategoriesUseCase.kt        # ⚠️ TODO: Implement
├── presentation/
│   ├── view/
│   │   ├── BookListView.kt                # ✅ With navigation button
│   │   └── CategoryListView.kt            # ⚠️ TODO: Update UI
│   └── viewmodel/
│       └── CategoryViewModel.kt           # ⚠️ TODO: Use GetCategoriesUseCase
└── MainActivity.kt                        # ✅ Navigation ready
```

### 3. Navigation ✅

A navigation button has been added to the top bar of `BookListView` that navigates to `CategoryListView`.

---

## 💻 Practical Exercises

### Exercise 1: Complete the Category Model (20 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/data/model/Category.kt`

**Current State:**
```kotlin
// TODO: Complete the Category data class implementation
data class Category(
    val placeholder: String = ""
)
```

**Your Task:**

Replace the placeholder with three proper attributes:

```kotlin
data class Category(
    val id: String,
    val name: String,
    val description: String
)
```

**Explanation:**
- **`id`**: Unique identifier for the category (e.g., "1", "2", "3")
- **`name`**: Category name (e.g., "Fiction", "Science", "History")
- **`description`**: Brief description of the category

**Example Category:**
```kotlin
Category(
    id = "1",
    name = "Programming",
    description = "Books about software development and coding"
)
```

---

### Exercise 2: Implement CategoryRepositoryImpl (30 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/data/repository/CategoryRepositoryImpl.kt`

**Current State:**
```kotlin
class CategoryRepositoryImpl : CategoryRepository {
    private val categoriesList = listOf<Category>()
    
    override fun getAllCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getCategoryById(id: String): Category? {
        TODO("Not yet implemented")
    }
}
```

**Your Task:**

#### Step 2.1: Create Sample Categories

Replace the empty list with at least **5 categories**:

```kotlin
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
    // Add 2 more categories here
)
```

**Suggested Categories:**
- Mobile Development
- Web Development
- Artificial Intelligence
- Computer Networks
- Software Engineering
- Cybersecurity
- Operating Systems

#### Step 2.2: Implement getAllCategories()

Replace the TODO with:

```kotlin
override fun getAllCategories(): List<Category> {
    return categoriesList
}
```

#### Step 2.3: Implement getCategoryById()

Replace the TODO with:

```kotlin
override fun getCategoryById(id: String): Category? {
    return categoriesList.find { it.id == id }
}
```

**Explanation:**
- `.find { }` searches the list for an element matching the condition
- Returns the first matching category or `null` if not found
- The `{ it.id == id }` is a lambda that checks if the category's id matches

---

### Exercise 3: Implement GetCategoriesUseCase (15 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/domain/usecase/GetCategoriesUseCase.kt`

**Current State:**
```kotlin
class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): List<Category> {
        TODO("Not yet implemented")
    }
}
```

**Your Task:**

Replace the TODO with:

```kotlin
operator fun invoke(): List<Category> {
    return categoryRepository.getAllCategories()
}
```

**Explanation:**
- The use case calls the repository to get all categories
- The `operator fun invoke()` allows calling the use case like a function: `getCategoriesUseCase()`
- This layer allows you to add business logic later (e.g., sorting, filtering)

---

### Exercise 4: Update CategoryViewModel (20 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/presentation/viewmodel/CategoryViewModel.kt`

**Current State:**
```kotlin
class CategoryViewModel : ViewModel() {
    // ... StateFlow declarations ...
    
    private fun loadCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: Use GetCategoriesUseCase instead of dummy data
                // val categoryList = getCategoriesUseCase()
                // _categories.value = categoryList
                
                // Dummy data for demonstration
                _categories.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

**Your Task:**

#### Step 4.1: Add GetCategoriesUseCase Parameter

Update the class constructor:

```kotlin
class CategoryViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
```

#### Step 4.2: Use the Use Case

Replace the dummy data section with:

```kotlin
private fun loadCategories() {
    viewModelScope.launch {
        _isLoading.value = true
        try {
            val categoryList = getCategoriesUseCase()
            _categories.value = categoryList
        } finally {
            _isLoading.value = false
        }
    }
}
```

---

### Exercise 5: Update MainActivity with Dependency Injection (15 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/MainActivity.kt`

**Current State:**
```kotlin
val categoryViewModel = CategoryViewModel()
```

**Your Task:**

Update the dependency injection to create and inject the use case:

```kotlin
// Category dependencies
val categoryRepository = CategoryRepositoryImpl()
val getCategoriesUseCase = GetCategoriesUseCase(categoryRepository)
val categoryViewModel = CategoryViewModel(getCategoriesUseCase)
```

**Full onCreate() should look like:**

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    
    // Book dependencies
    val bookRepository = BookRepositoryImpl()
    val getBooksUseCase = GetBooksUseCase(bookRepository)
    val bookViewModel = BookViewModel(getBooksUseCase)
    
    // Category dependencies
    val categoryRepository = CategoryRepositoryImpl()
    val getCategoriesUseCase = GetCategoriesUseCase(categoryRepository)
    val categoryViewModel = CategoryViewModel(getCategoriesUseCase)
    
    setContent {
        MaktabaTheme {
            var showCategories by remember { mutableStateOf(false) }
            
            if (showCategories) {
                CategoryListView(
                    viewModel = categoryViewModel,
                    onBackClick = { showCategories = false }
                )
            } else {
                BookListView(
                    viewModel = bookViewModel,
                    onCategoriesClick = { showCategories = true }
                )
            }
        }
    }
}
```

---

### Exercise 6: Update CategoryListView UI (30 minutes)

**Location:** `app/src/main/java/com/ElOuedUniv/maktaba/presentation/view/CategoryListView.kt`

**Current State:**

The `CategoryItem` composable currently shows placeholder text:

```kotlin
@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Category Item",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
```

**Your Task:**

Update the `CategoryItem` to display actual category data:

```kotlin
@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

---

### Exercise 7: Run and Test (15 minutes)

1. **Build the project:**
   ```bash
   ./gradlew build
   ```

2. **Run the application:**
   - Click the green "Run" button in Android Studio
   - Or use: `Shift+F10` (Windows/Linux) or `Ctrl+R` (Mac)

3. **Test the navigation:**
   - The app should open showing the book list
   - Click the **menu icon** (☰) in the top-right corner
   - You should navigate to the Categories screen
   - Verify that your 5+ categories are displayed
   - Each category card should show:
     - Category name (bold)
     - Category description
   - Click the **back arrow** to return to books

4. **Verify the data flow:**
   - Categories should load from `CategoryRepositoryImpl`
   - Through `GetCategoriesUseCase`
   - Into `CategoryViewModel`
   - Displayed in `CategoryListView`

---

## 🔍 Understanding the Complete Flow

Let's trace what happens when you click the Categories button:

```
1. User clicks Menu icon in BookListView
   ↓
2. MainActivity sets showCategories = true
   ↓
3. CategoryListView is displayed
   ↓
4. CategoryViewModel.init { } runs
   ↓
5. loadCategories() is called
   ↓
6. getCategoriesUseCase() is invoked
   ↓
7. categoryRepository.getAllCategories()
   ↓
8. Returns List<Category>
   ↓
9. _categories.value = categoryList (StateFlow update)
   ↓
10. CategoryListView observes the change
   ↓
11. UI recomposes and displays categories
```

---

## 🧪 Bonus Exercises (Optional)

### Bonus 1: Add Category Count

**Task:** Display the total number of categories at the top of `CategoryListView`.

**Hint:** Add this before the `LazyColumn`:
```kotlin
Text(
    text = "Total Categories: ${categories.size}",
    style = MaterialTheme.typography.titleMedium,
    modifier = Modifier.padding(16.dp)
)
```

### Bonus 2: Search Category by ID

**Task:** Add a function to `CategoryViewModel` that finds a category by ID.

```kotlin
fun getCategoryById(id: String): Category? {
    return categories.value.find { it.id == id }
}
```

### Bonus 3: Sort Categories Alphabetically

**Task:** Modify `GetCategoriesUseCase` to return categories sorted by name.

```kotlin
operator fun invoke(): List<Category> {
    return categoryRepository.getAllCategories().sortedBy { it.name }
}
```

---

## 📝 Code Analysis Questions

Answer these questions to deepen your understanding:

### Question 1: Repository Pattern
**Q:** Why do we use `CategoryRepository` (interface) and `CategoryRepositoryImpl` (implementation) instead of just one class?

**A:** _(Write your answer)_

### Question 2: Use Case Layer
**Q:** What is the purpose of `GetCategoriesUseCase`? Why not call the repository directly from the ViewModel?

**A:** _(Write your answer)_

### Question 3: StateFlow
**Q:** In `CategoryViewModel`, why do we have both `_categories` (private MutableStateFlow) and `categories` (public StateFlow)?

**A:** _(Write your answer)_

### Question 4: Dependency Injection
**Q:** In `MainActivity`, we manually create instances and pass them as constructor parameters. What is this pattern called? What are the alternatives?

**A:** _(Write your answer)_

---

## 📤 How to Submit Your Work

### Submission Method: GitHub Pull Request

1. **Create a new branch:**
   ```bash
   git checkout -b tp2-yourname
   ```

2. **Complete all exercises**

3. **Commit your changes:**
   ```bash
   git add .
   git commit -m "TP2: Implement Category management with MVVM"
   ```

4. **Push your branch:**
   ```bash
   git push origin tp2-yourname
   ```

5. **Create a Pull Request** on GitHub

---

## 📖 Additional Resources

### Kotlin Resources
- [Kotlin Interfaces](https://kotlinlang.org/docs/interfaces.html)
- [Kotlin Collections - find()](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/find.html)
- [Kotlin Lambda Expressions](https://kotlinlang.org/docs/lambdas.html)

### Android Resources
- [Repository Pattern](https://developer.android.com/topic/architecture/data-layer)
- [Dependency Injection Manual](https://developer.android.com/training/dependency-injection/manual)
- [StateFlow Best Practices](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

### Design Patterns
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)

---

## ✅ Checklist

Before submitting, make sure you've completed:

- [ ] Exercise 1: Added `id`, `name`, and `description` to `Category.kt`
- [ ] Exercise 2: Created 5+ categories in `CategoryRepositoryImpl`
- [ ] Exercise 2: Implemented `getAllCategories()`
- [ ] Exercise 2: Implemented `getCategoryById()`
- [ ] Exercise 3: Implemented `GetCategoriesUseCase`
- [ ] Exercise 4: Updated `CategoryViewModel` to use the use case
- [ ] Exercise 5: Updated `MainActivity` with proper dependency injection
- [ ] Exercise 6: Updated `CategoryListView` to display category data
- [ ] Exercise 7: Tested the app and verified navigation works
- [ ] Answered the code analysis questions
- [ ] (Optional) Completed bonus exercises

---

**Good luck! 🚀**

**Made with ❤️ for El Oued University CS Students**
