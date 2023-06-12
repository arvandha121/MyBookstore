package com.fitdev.mybookstore.repository

import com.fitdev.mybookstore.data.Book
import com.fitdev.mybookstore.data.BooksData

class BookRepository {
    fun getBooks(): List<Book> {
        return BooksData.books
    }

    fun searchBooks(query: String): List<Book>{
        return BooksData.books.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}