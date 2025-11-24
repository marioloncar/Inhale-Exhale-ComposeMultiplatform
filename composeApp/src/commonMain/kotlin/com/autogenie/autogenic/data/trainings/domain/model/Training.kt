package com.autogenie.autogenic.data.trainings.domain.model

data class Training(
    val id: String,
    val name: String,
    val summary : String,
    val description: String,
    val cycles: Int,
    val steps: List<TrainingStep>
)

data class TrainingStep(
    val type: StepType,
    val duration: Int
)

enum class StepType {
    INHALE,
    EXHALE,
    HOLD
}
