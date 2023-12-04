package com.gigacrew.dreamjournal

data class Dream(
    var dreamId: String = "0",
    val userId: String = "",
    val title: String ="0",
    val recurringDream: Boolean = false,
    val imageURL: String ="",
    val feeling: ArrayList<String> = java.util.ArrayList(6),
    val dreamDescription: String ="",
    val date: String="",
    var category: String=""
)
