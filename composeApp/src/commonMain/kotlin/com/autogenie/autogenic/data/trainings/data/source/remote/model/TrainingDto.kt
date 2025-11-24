package com.autogenie.autogenic.data.trainings.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainingDto(
    val id: String,
    val name: String,
    val summary: String,
    val description: String,
    val cycles: Int,
    val steps: List<StepDto>
)

@Serializable
data class StepDto(
    val type: String,
    val duration: Int
)
