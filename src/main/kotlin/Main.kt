import controllers.LibraryAPI
import io.github.oshai.kotlinlogging.KotlinLogging
import models.Book
import utils.readNextInt
import utils.readNextLine
import java.util.Scanner

private val logger = KotlinLogging.logger {}
private val libraryAPI = LibraryAPI()
private val scanner = Scanner(System.`in`)



fun readNextLine(prompt: String): String {
    print(prompt)
    return scanner.nextLine()
}

fun readNextInt(prompt: String): Int {
    print(prompt)
    return scanner.nextInt().also { scanner.nextLine() } // consume newline
}





fun main() {
    runMenu()
}

fun mainMenu(): Int {
    print(
        """
         ----------------------------------
         |     LIBRARY MANAGEMENT APP     |
         ----------------------------------
         | MAIN MENU                      |
         |   1) Add Books 
         |   2) List All Books            |
         |   3) Update a Book             |
         |   4) Return a Book             |
         |   5) List All Borrowed Books   |
         |   6) Delete Books              |
         ----------------------------------
         |   0) Exit                      |
         ----------------------------------
        """.trimIndent()
    )
    return readNextInt(" > ==>> ")
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addBook()
            2 -> listAllBooks()
            3 -> updateBook()
            4 -> returnBook()
            5 -> listBorrowedBooks()
            6 -> deleteBook()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addBook() {
    val bookTitle = readNextLine("Enter the title of the book: ")
    val bookAuthor = readNextLine("Enter the author of the book: ")
    val bookGenre = readNextLine("Enter the genre of the book: ")
    val isAdded = libraryAPI.add(Book(bookTitle, bookAuthor, bookGenre))

    if (isAdded) {
        println("Book added successfully!")
    } else {
        println("Failed to add the book.")
    }
}

fun listAllBooks() {
    val books = libraryAPI.listAllBooks()
    if (books.isEmpty()) {
        println("No books available.")
    } else {
        books.forEachIndexed { index, book ->
            println("${index + 1}: $book")
        }
    }
}

fun updateBook() {
    listAllBooks()
    if (libraryAPI.numberOfBooks() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the book to update: ") - 1
        if (libraryAPI.isValidIndex(indexToUpdate)) {
            val bookTitle = readNextLine("Enter the new title of the book: ")
            val bookAuthor = readNextLine("Enter the new author of the book: ")
            val bookGenre = readNextLine("Enter the new genre of the book: ")

            val isUpdated = libraryAPI.updateBook(indexToUpdate, Book(bookTitle, bookAuthor, bookGenre))
            if (isUpdated) {
                println("Book updated successfully!")
            } else {
                println("Failed to update the book.")
            }
        } else {
            println("Invalid index. Please try again.")
        }
    } else {
        println("No books available to update.")
    }
}

fun returnBook() {
    listAllBooks()
    if (libraryAPI.numberOfBooks() > 0) {
        val index = readNextInt("Enter the index of the book to return: ") - 1
        if (libraryAPI.returnBook(index)) {
            println("Book returned successfully!")
        } else {
            println("Failed to return book (maybe it wasn’t borrowed).")
        }
    }
}

fun listBorrowedBooks() {
    val borrowed = libraryAPI.listBorrowedBooks()
    if (borrowed.isEmpty()) {
        println("No books are currently borrowed.")
    } else {
        borrowed.forEachIndexed { index, book ->
            println("${index + 1}: $book")
        }
    }
}


fun deleteBook() {
    listAllBooks()
    if (libraryAPI.numberOfBooks() > 0) {
        val indexToDelete = readNextInt("Enter the index of the book to delete: ") - 1
        val bookToDelete = libraryAPI.deleteBook(indexToDelete)
        if (bookToDelete != null) {
            println("Delete Successful! Deleted book: ${bookToDelete.title}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun exitApp() {
    logger.info { "exitApp() function invoked" }
    println("Exiting...bye")
    kotlin.system.exitProcess(0)
}


