# 📚 Maktaba - Library Management System

## 🚀 إعداد قاعدة البيانات (Supabase)

لإعداد المشروع، قم بتنفيذ كود SQL التالي في **Supabase SQL Editor**:

```sql
-- 1. جدول التصنيفات
CREATE TABLE categories (
    id TEXT PRIMARY KEY DEFAULT gen_random_uuid()::text,
    name TEXT NOT NULL,
    description TEXT
);

-- 2. جدول الكتب (يحتوي على المفضلة والوصف)
CREATE TABLE books (
    isbn TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    "nbPages" INTEGER NOT NULL DEFAULT 0,
    "description" TEXT,
    "imageUrl" TEXT,
    "pdfUrl" TEXT,
    "categoryId" TEXT REFERENCES categories(id),
    "isFavorite" BOOLEAN DEFAULT FALSE
);

-- 3. إعدادات الأمان (Storage Policies)
-- يجب إنشاء Bucket باسم 'book_pdfs' في قسم Storage وجعله Public
CREATE POLICY "Allow Public Access" ON storage.objects FOR SELECT USING (bucket_id = 'book_pdfs');
CREATE POLICY "Allow Public Upload" ON storage.objects FOR INSERT WITH CHECK (bucket_id = 'book_pdfs');
CREATE POLICY "Allow Public Update" ON storage.objects FOR UPDATE USING (bucket_id = 'book_pdfs');
```

## ✨ الميزات الحالية
- ✅ ربط كامل مع Supabase (قاعدة بيانات وتخزين ملفات).
- ✅ دعم رفع وقراءة ملفات الـ PDF برمجياً.
- ✅ نظام تصنيفات للكتب.
- ✅ دعم الميزة المفضلة (Favorite).
- ✅ واجهة مستخدم حديثة بـ Jetpack Compose.
