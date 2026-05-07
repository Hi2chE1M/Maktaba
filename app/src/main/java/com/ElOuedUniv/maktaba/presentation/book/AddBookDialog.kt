package com.ElOuedUniv.maktaba.presentation.book

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ElOuedUniv.maktaba.data.model.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookDialog(
    categories: List<Category>,
    onDismiss: () -> Unit,
    onConfirm: (title: String, isbn: String, nbPages: Int, categoryId: String?, pdfBytes: ByteArray?, fileName: String?) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var nbPages by remember { mutableStateOf("") }
    
    // لإدارة اختيار التصنيف
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    
    // لإدارة اختيار ملف PDF
    val context = LocalContext.current
    var pdfUri by remember { mutableStateOf<Uri?>(null) }
    var pdfFileName by remember { mutableStateOf<String?>(null) }
    var pdfBytes by remember { mutableStateOf<ByteArray?>(null) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pdfUri = uri
        uri?.let {
            pdfFileName = "book_${System.currentTimeMillis()}.pdf"
            pdfBytes = context.contentResolver.openInputStream(it)?.readBytes()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "إضافة كتاب جديد") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("عنوان الكتاب") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    label = { Text("ISBN") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = nbPages,
                    onValueChange = { nbPages = it },
                    label = { Text("عدد الصفحات") },
                    modifier = Modifier.fillMaxWidth()
                )

                // اختيار التصنيف
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.name ?: "اختر التصنيف",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("التصنيف") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // اختيار ملف PDF
                Button(
                    onClick = { pdfPickerLauncher.launch("application/pdf") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (pdfUri != null) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (pdfUri != null) "تم اختيار ملف PDF ✓" else "اختيار ملف PDF")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        title, 
                        isbn, 
                        nbPages.toIntOrNull() ?: 0, 
                        selectedCategory?.id,
                        pdfBytes,
                        pdfFileName
                    )
                },
                enabled = title.isNotBlank() && isbn.isNotBlank()
            ) {
                Text("إضافة")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}
