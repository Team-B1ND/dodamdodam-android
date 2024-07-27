package com.b1nd.dodam.logging

interface TagProvider {
    fun createTag(fromClass: String?): Pair<String, String>
}