package com.example.Drizzle

data class ChatChannel (val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}