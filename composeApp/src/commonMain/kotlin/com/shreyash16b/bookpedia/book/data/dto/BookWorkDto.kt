package com.shreyash16b.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description : String? = null
)
