# TP 4: Advanced Jetpack Compose UI & Validation

![Design Sketch](sketch.png)

## Objective
In this TP, you will transform the "light" UI implementation into a premium, interactive experience based on the provided design sketch. You will also learn to handle modern navigation, update data models, and implement advanced input validation.

---

## 1. Navigation Logic Overview

Before diving into the UI, it's crucial to understand how the app navigates between screens. The project uses `androidx.navigation:navigation-compose` along with Hilt for dependency injection.

### Key Components:
- **`Screen.kt`**: A sealed class defining all available routes (e.g., `Onboarding`, `BookList`, `BookDetail`).
- **`NavGraph.kt`**: The central navigation host that maps routes to Composable screens and handles the "back stack".
- **`MainActivity.kt`**: No longer holds UI state; it simply hosts the `NavGraph`.

**Your Task**: Open `NavGraph.kt` and observe how events (lambdas) like `onBookClick` are used to trigger navigation. You will need to use these lambdas in your Composable implementations.

---

## 2. Model Updates

To support the new design, we need to enrich our data models.

### Task 2.1: Update `Book` Model
Add an `imageUrl: String?` parameter to the `Book` data class in `com.ElOuedUniv.maktaba.data.model`. This will allow us to display book covers.

### Task 2.2: Update `Category` Model
Update the `Category` data class to include an `iconRes: Int` parameter. This will store the drawable resource ID for the category icon.

---

## 3. Screen-by-Screen Implementation

Using **Jetpack Compose**, implement the following screens as shown in the design sketch.

### 3.1 Book List (My Library)
- **Layout**: Use a `Vertical Grid` (e.g., `LazyVerticalGrid`) to show books in two columns.
- **Components**: Create a `BookCard` that displays the cover image, title, ISBN, and reading status (e.g., "Reading", "Finished").
- **Interaction**: Clicking a card should call the `onBookClick` lambda to navigate to the details.

### 3.2 Book Details
- **Hero Image**: Display the book cover prominently at the top.
- **Metadata**: Show reading progress, total pages, and ISBN using clear icons and typography.
- **Transitions**: Ensure the transition from the list to this view feels smooth.

### 3.3 Category Explorer
- **List Layer**: Use a `LazyColumn` for categories.
- **Visuals**: Each category should show its custom icon (using the new `iconRes` param) and a description.
- **Aesthetics**: Use rounded corners and subtle shadows to make the list feel premium.

### 3.4 Add Book Flow
- **Form**: Implement a full-screen view with inputs for Title, ISBN, and Pages.
- **Image Picker**: Add an area to "Add Cover Image" (you can simulate this with a placeholder for now).
- **Navigation**: The "Cancel" and "Success" actions should correctly pop the back stack.

---

## 4. [Bonus] Advanced Input Validation

Implement validation for the **Add Book** screen:
- **Real-time Checks**: The "Confirm" button should only be enabled if all fields are valid.
- **Rules**: 
    - Title cannot be empty.
    - ISBN must be exactly 13 digits.
    - Pages must be a positive number.
- **Architecture**: Move this logic into the `AddBookViewModel` using the `onAction` and `uiState` pattern.
    - Create a `validateInputs()` function in the ViewModel.
    - Update `AddBookUiState` to include `isFormValid: Boolean` and specific field error messages.

---

## Resources
- [Jetpack Compose Layouts](https://developer.android.com/jetpack/compose/layouts)
- [Navigation with Compose](https://developer.android.com/jetpack/compose/navigation)
- [Hilt for Navigation](https://developer.android.com/training/dependency-injection/hilt-jetpack)
