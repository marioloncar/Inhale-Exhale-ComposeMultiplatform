package com.autogenie.autogenic.data.trainings.data.source.remote

import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto
import kotlinx.serialization.json.Json

class TrainingsRemoteSourceImpl : TrainingsRemoteSource {

    val trainingsJson = """
[
  {
    "id": "Breathing_1",
    "name": "Box Breathing",
    "instructions": [
      "Inhale for 4 seconds.",
      "Hold your breath for 4 seconds.",
      "Exhale for 4 seconds.",
      "Hold your breath again for 4 seconds.",
      "Repeat the cycle calmly."
    ]
  },
  {
    "id": "Breathing_2",
    "name": "4-7-8 Breathing",
    "instructions": [
      "Inhale quietly through your nose for 4 seconds.",
      "Hold your breath for 7 seconds.",
      "Exhale slowly through your mouth for 8 seconds.",
      "Repeat to relax your nervous system."
    ]
  },
  {
    "id": "Breathing_3",
    "name": "Alternate Nostril",
    "instructions": [
      "Close your right nostril and inhale through the left.",
      "Close both nostrils and hold.",
      "Exhale through the right nostril.",
      "Inhale through the right, hold, and exhale through the left.",
      "Repeat this alternate cycle."
    ]
  },
  {
    "id": "Breathing_4",
    "name": "Deep Diaphragmatic",
    "instructions": [
      "Place one hand on your chest and the other on your stomach.",
      "Inhale deeply through your nose for 4 seconds.",
      "Feel your stomach rise, not your chest.",
      "Exhale slowly through pursed lips.",
      "Repeat while focusing on your breath."
    ]
  },
  {
    "id": "Breathing_5",
    "name": "Coherent Breathing",
    "instructions": [
      "Inhale gently for 5 seconds.",
      "Exhale gently for 5 seconds.",
      "Maintain a steady rhythm for several minutes.",
      "Let your breath guide you into calm."
    ]
  }
]
""".trimIndent()

    override suspend fun fetchTrainings(): List<TrainingDto> {
        return Json.decodeFromString(trainingsJson)
    }
}
