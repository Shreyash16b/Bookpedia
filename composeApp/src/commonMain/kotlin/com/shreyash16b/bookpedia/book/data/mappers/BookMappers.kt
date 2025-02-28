package com.shreyash16b.bookpedia.book.data.mappers

import com.shreyash16b.bookpedia.book.data.database.BookEntity
import com.shreyash16b.bookpedia.book.data.dto.SearchedBookDto
import com.shreyash16b.bookpedia.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity() : BookEntity{
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishedYear = firstPublishYear,
        numEditions = numEditions,
        numPagesMedian = numPages,
        ratingsAverage = averageRating,
        ratingsCount = ratingCount
    )
}

fun BookEntity.toBook() : Book{
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear= firstPublishedYear,
        numEditions = numEditions,
        numPages = numPagesMedian,
        averageRating = ratingsAverage ,
        ratingCount = ratingsCount
    )
}