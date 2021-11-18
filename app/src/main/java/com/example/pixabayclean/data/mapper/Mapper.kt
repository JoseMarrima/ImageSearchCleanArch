package com.example.pixabayclean.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}