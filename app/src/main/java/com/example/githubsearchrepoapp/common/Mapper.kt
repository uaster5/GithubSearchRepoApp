package com.example.githubsearchrepoapp.common

interface Mapper<in T, out R> {

    fun map(item: T): R

    fun map(items: List<T>): List<R> = items.map(::map)
}