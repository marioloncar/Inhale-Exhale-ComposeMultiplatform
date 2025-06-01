package com.autogenie.autogenic.data.trainings.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("color")
    val colorHex: String,
    @SerialName("instructions")
    val instructions: List<String>
)
