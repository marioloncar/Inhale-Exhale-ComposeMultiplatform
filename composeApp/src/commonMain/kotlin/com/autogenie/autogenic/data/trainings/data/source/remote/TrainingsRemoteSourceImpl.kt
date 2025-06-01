package com.autogenie.autogenic.data.trainings.data.source.remote

import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto
import kotlinx.serialization.json.Json

class TrainingsRemoteSourceImpl : TrainingsRemoteSource {

    val trainingsJson = """
    [
      {
        "id": "Exercise_1",
        "name": "Heaviness",
        "color": "#2196F3",
        "instructions": [
          "My right arm is heavy.",
          "My left arm is heavy.",
          "My arms and legs are heavy.",
          "I am feeling calm and relaxed."
        ]
      },
      {
        "id": "Exercise_2",
        "name": "Warmth",
        "color": "#FF9800",
        "instructions": [
          "My right arm is warm.",
          "My left arm is warm.",
          "My arms and legs are pleasantly warm.",
          "Warmth is flowing through my body."
        ]
      },
      {
        "id": "Exercise_3",
        "name": "Heartbeat",
        "color": "#F44336",
        "instructions": [
          "My heartbeat is calm and regular.",
          "I feel my pulse steadily and peacefully.",
          "My heart is beating quietly and evenly."
        ]
      },
      {
        "id": "Exercise_4",
        "name": "Solar Plexus",
        "color": "#FFEB3B",
        "instructions": [
          "My breathing is calm and regular.",
          "Warmth flows into my stomach area.",
          "My solar plexus radiates warmth."
        ]
      },
      {
        "id": "Exercise_5",
        "name": "Forehead",
        "color": "#4CAF50",
        "instructions": [
          "My forehead is pleasantly cool.",
          "I feel a cool sensation in my forehead.",
          "My mind is clear and calm."
        ]
      }
    ]
""".trimIndent()

    override suspend fun fetchTrainings(): List<TrainingDto> {
        return Json.decodeFromString(trainingsJson)
    }
}
