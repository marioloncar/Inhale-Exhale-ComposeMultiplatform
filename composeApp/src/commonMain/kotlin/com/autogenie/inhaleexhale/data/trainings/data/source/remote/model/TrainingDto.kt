package com.autogenie.inhaleexhale.data.trainings.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDto(
    val id: String,
    val name: String,
    val category: CategoryDto,
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

@Serializable
enum class CategoryDto {
    Sleep,
    @SerialName("Stress Relief")
    StressRelief,
    Focus,
    Energy,
    @SerialName("Quick Break")
    QuickBreak
}