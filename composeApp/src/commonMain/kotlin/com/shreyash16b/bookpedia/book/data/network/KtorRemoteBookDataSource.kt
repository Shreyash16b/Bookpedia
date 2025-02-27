package com.shreyash16b.bookpedia.book.data.network

import com.shreyash16b.bookpedia.book.data.dto.BookWorkDto
import com.shreyash16b.bookpedia.book.data.dto.SearchResponseDto
import com.shreyash16b.bookpedia.core.data.safeCall
import com.shreyash16b.bookpedia.core.domain.DataError
import com.shreyash16b.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun getBookDescription(bookWordId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWordId.json"
            )
        }
    }
}