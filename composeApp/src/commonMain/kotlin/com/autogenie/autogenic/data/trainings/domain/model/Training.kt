package com.autogenie.autogenic.data.trainings.domain.model

data class Training(
    val id: String,
    val name: String,
    val instructions: List<String>
)
