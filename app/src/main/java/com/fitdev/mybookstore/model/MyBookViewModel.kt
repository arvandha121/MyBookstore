package com.fitdev.mybookstore.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitdev.mybookstore.repository.BookRepository
import com.fitdev.mybookstore.data.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyBookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow(
        repository.getBooks()
            .sortedBy { it.name }
    )

    val books: StateFlow<List<Book>> get() = _books

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _books.value = repository.searchBooks(_query.value)
            .sortedBy { it.name }
    }
}

class ViewModelFactory(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyBookViewModel::class.java)) {
            return MyBookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}