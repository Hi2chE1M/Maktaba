package com.ElOuedUniv.maktaba.presentation.book.detail

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    // مراقبة الأخطاء وعرضها
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, "خطأ: $it", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onBackClick()
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("حذف الكتاب") },
            text = { Text("هل أنت متأكد من رغبتك في حذف هذا الكتاب؟") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onAction(BookDetailUiAction.OnDeleteClick)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("حذف")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("إلغاء")
                }
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("تفاصيل الكتاب") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditClick(uiState.book?.isbn ?: "") }) {
                        Icon(Icons.Default.Edit, contentDescription = "تعديل")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "حذف", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                uiState.book != null -> {
                    val book = uiState.book!!
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            BookHeader(book)
                        }
                        
                        item {
                            BookActions(
                                book = book,
                                onReadClick = {
                                    book.pdfUrl?.let { url ->
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                                setDataAndType(Uri.parse(url), "application/pdf")
                                                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                                            }
                                            context.startActivity(Intent.createChooser(intent, "افتح الكتاب باستخدام:"))
                                        } catch (e: Exception) {
                                            // إذا لم يتوفر تطبيق PDF، افتحه في المتصفح
                                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                            context.startActivity(browserIntent)
                                        }
                                    } ?: run {
                                        Toast.makeText(context, "لا يوجد ملف PDF متاح", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onFavoriteClick = {
                                    viewModel.onAction(BookDetailUiAction.OnFavoriteClick)
                                }
                            )
                        }

                        item {
                            BookInfoSection(book)
                        }

                        if (!book.description.isNullOrBlank()) {
                            item {
                                Text(
                                    text = "عن الكتاب",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = book.description,
                                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookHeader(book: Book) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.width(180.dp).aspectRatio(0.7f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            if (!book.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BookActions(
    book: Book, 
    onReadClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val favoriteColor by animateColorAsState(
        if (book.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "favoriteColor"
    )

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onReadClick,
            modifier = Modifier.weight(1f).height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !book.pdfUrl.isNullOrEmpty()
        ) {
            Icon(Icons.Default.AutoStories, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("قراءة الكتاب")
        }
        
        OutlinedButton(
            onClick = onFavoriteClick,
            modifier = Modifier.height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, if (book.isFavorite) Color.Red.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline)
        ) {
            Icon(
                imageVector = if (book.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = favoriteColor
            )
        }
    }
}

@Composable
private fun BookInfoSection(book: Book) {
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoColumn(label = "الصفحات", value = if (book.nbPages > 0) book.nbPages.toString() else "--")
        VerticalDivider(modifier = Modifier.height(40.dp).align(Alignment.CenterVertically))
        InfoColumn(label = "النوع", value = "PDF")
        VerticalDivider(modifier = Modifier.height(40.dp).align(Alignment.CenterVertically))
        InfoColumn(label = "ISBN", value = book.isbn.takeLast(5))
    }
}

@Composable
private fun InfoColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
    }
}
