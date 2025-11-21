package com.autogenie.autogenic.data.trainings.data.source.remote

import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto
import kotlinx.serialization.json.Json

class TrainingsRemoteSourceImpl : TrainingsRemoteSource {

    val trainingsJson = """
[
  {
    "id": "box",
    "name": "Box Breathing",
    "cycles": 4,
    "steps": [
      { "type": "inhale", "duration": 4 },
      { "type": "hold",   "duration": 4 },
      { "type": "exhale", "duration": 4 },
      { "type": "hold",   "duration": 4 }
    ]
  },
  {
    "id": "478",
    "name": "4-7-8 Breathing",
    "cycles": 4,
    "steps": [
      { "type": "inhale", "duration": 4 },
      { "type": "hold",   "duration": 7 },
      { "type": "exhale", "duration": 8 }
    ]
  },
  {
    "id": "physiological_sigh",
    "name": "Physiological Sigh",
    "cycles": 3,
    "steps": [
      { "type": "inhale", "duration": 2 },
      { "type": "inhale", "duration": 1 },
      { "type": "exhale", "duration": 6 }
    ]
  },
  {
    "id": "coherent",
    "name": "Coherent Breathing",
    "cycles": 10,
    "steps": [
      { "type": "inhale", "duration": 5 },
      { "type": "exhale", "duration": 5 }
    ]
  }
]
""".trimIndent()

    override suspend fun fetchTrainings(): List<TrainingDto> {
        return Json.decodeFromString(trainingsJson)
    }
}
