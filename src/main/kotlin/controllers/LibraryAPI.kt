package controllers

import models.Book

class LibraryAPI {
    private val books = mutableListOf<Book>()

    fun add(book: Book): Boolean {
        return books.add(book)
    }

    fun listAllBooks(): List<Book> {
        return books
    }

    fun numberOfBooks(): Int {
        return books.size
    }

    fun isValidIndex(index: Int): Boolean {
        return index in books.indices
    }

    fun updateBook(index: Int, updatedBook: Book): Boolean {
        return if (isValidIndex(index)) {
            books[index] = updatedBook
            true
        } else {
            false
        }
    }

    fun deleteBook(index: Int): Book? {
        return if (isValidIndex(index)) {
            books.removeAt(index)
        } else {
            null
        }
    }

    fun borrowBook(index: Int): Boolean {
        if (isValidIndex(index) && !books[index].isBorrowed) {
            books[index].isBorrowed = true
            return true
        }
        return false
    }

    fun returnBook(index: Int): Boolean {
        if (isValidIndex(index) && books[index].isBorrowed) {
            books[index].isBorrowed = false
            return true
        }
        return false
    }

    fun listBorrowedBooks(): List<Book> {
        return books.filter { it.isBorrowed }
    }
}





