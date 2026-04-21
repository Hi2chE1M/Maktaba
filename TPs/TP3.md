# TP3: Advanced MVVM, Dagger Hilt, and Reactive UI Patterns

**Course:** Mobile Application Development  
**University:** El Oued University (جامعة الوادي)  
**Level:** 3rd Year Computer Science  
**Duration:** 3 hours  
**Topic:** Implementing Reactive UI with State, Actions, and Events using Dagger Hilt

---

## 🎯 Learning Objectives

By the end of this practical session, you will be able to:

1. ✅ Integrate **Dagger Hilt** for Dependency Injection.
2. ✅ Use **KSP (Kotlin Symbol Processing)** for faster annotation processing.
3. ✅ Implement the **UI State pattern** using data classes.
4. ✅ Handle user interactions using **Sealed Interfaces (UI Actions)**.
5. ✅ Manage one-time effects (System Events) using **UI Events**.
6. ✅ Work with **SharedFlow** and **StateFlow** for asynchronous data streams.
7. ✅ Perform advanced **package refactoring** to a feature-based structure.

---

## 📂 Project Evolution

The project has been refactored from a **layer-based** package structure to a **feature-based** structure. This is a common practice in large-scale Android applications.

```
presentation/
├── book/
│   ├── BookViewModel.kt
│   ├── BookListView.kt
│   ├── BookUiState.kt    <-- 🆕 Data Class
│   ├── BookUiAction.kt   <-- 🆕 Sealed Interface (User Input)
│   └── BookUiEvent.kt    <-- 🆕 Sealed Interface (System Event)
└── category/
    ├── CategoryViewModel.kt
    ├── CategoryListView.kt
    ├── CategoryUiState.kt
    ├── CategoryUiAction.kt
    └── CategoryUiEvent.kt
```

---

## 💻 Practical Exercises

### Exercise 1: Understanding UI State (30 minutes)

**Locations:** `BookUiState.kt` and `BookViewModel.kt`

Instead of having multiple `MutableStateFlow` variables (like `_books`, `_isLoading`), we now use a single `UiState` object.

**Your Task:**
1. Open `BookViewModel.kt`.
2. Refactor the ViewModel to use a single `MutableStateFlow<BookUiState>`.
3. Update the `loadBooks()` method to update the state object correctly using `.copy()`.

**Example:**
```kotlin
private val _uiState = MutableStateFlow(BookUiState())
val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

// To update:
_uiState.update { it.copy(isLoading = true) }
```

---

### Exercise 2: Implementing "Add Book" Logic (45 minutes)

**Locations:** `BookRepositoryImpl.kt` and `AddBookUseCase.kt`

A Floating Action Button (FAB) has been added to `BookListView.kt`. You need to implement the backend logic to make it work.

**Your Task:**
1. Open `BookRepositoryImpl.kt`.
2. Implement the `addBook(book: Book)` method.
3. **Hint:** Since we use `MutableSharedFlow`, you need to emit the updated list after adding the book to your internal list.
4. Open `AddBookUseCase.kt` and complete the implementation by calling the repository.

---

### Exercise 3: UI Actions & Events (45 minutes)

**Locations:** `BookUiAction.kt`, `BookUiEvent.kt`, and `BookViewModel.kt`

**Actions** are things the user does (clicks, types). **Events** are things the system tells the UI to do (show toast, navigate).

**Your Task:**
1. In `BookViewModel.kt`, create a function `onAction(action: BookUiAction)`.
2. Use a `when` expression to handle each action defined in `BookUiAction.kt`.
3. Implement a `uiEvent` flow in the ViewModel to send one-time events.
4. In `BookListView.kt`, connect the FAB click to the `OnAddBookClick` action.

---

### Exercise 4: Add Book Dialog (30 minutes)

**Locations:** `AddBookDialog.kt` and `BookListView.kt`

A dialog component `AddBookDialog` has been prepared for you. You need to show it when the user clicks the FAB.

**Your Task:**
1. Open `BookListView.kt`.
2. Observe the `uiState` (which you refactored in Exercise 1).
3. Use an `if` condition to display `AddBookDialog` when `uiState.isAddingBook` is true.
4. Pass the appropriate actions (`OnDismissAddBook`, `OnAddBookConfirm`) to the dialog's parameters.

---

### Exercise 5: Hilt Dependency Injection (30 minutes)

**Locations:** `DataModule.kt` and `DomainModule.kt`

Dagger Hilt is now configured to handle dependency injection automatically.

**Your Task:**
1. Analyze how `DataModule` uses `@Provides` to provide repository implementations.
2. Analyze how `DomainModule` provides the Use Cases.
3. Explain why we use `hiltViewModel()` in our Composables instead of passing them from `MainActivity`.

---

## 🔍 Code Analysis Questions

1. **Package Structure**: Why is feature-based decomposition better than layer-based decomposition for large projects?
2. **Sealed Interfaces**: What is the advantage of using a `sealed interface` for `UiAction` compared to regular functions in the ViewModel?
3. **SharedFlow vs StateFlow**: In `BookRepositoryImpl`, we used `SharedFlow`. Why might this be chosen over `StateFlow` for a data stream that emits updates?

---

## 📝 Submission

Follow the same Git workflow as TP2:
1. Create a branch `tp3-yourname`.
2. Complete all exercises and TODOs.
3. Push and create a Pull Request.

**Good luck! 🚀**
