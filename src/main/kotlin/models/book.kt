package models

data class Book(
    var title: String,
    var author: String,
    var genre: String,
    var isBorrowed: Boolean = false
) {
    override fun toString(): String {
        return "Title: $title, Author: $author, Genre: $genre, Borrowed: $isBorrowed"
    }
}

