package com.autogenie.autogenic.data.trainings.data.source.remote

import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto
import kotlinx.serialization.json.Json

class TrainingsRemoteSourceImpl : TrainingsRemoteSource {

    val trainingsJson = """
[
  {
    "id": "box",
    "name": "Box Breathing",
    "description": "A balanced 4-4-4-4 pattern that improves focus and reduces stress.",
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
    "description": "A calming pattern designed to slow heart rate and ease anxiety.",
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
    "description": "A quick stress-reset using a double inhale followed by a long exhale.",
    "cycles": 3,
    "steps": [
      { "type": "inhale", "duration": 2 },
      { "type": "hold",   "duration": 1 },
      { "type": "inhale", "duration": 1 },
      { "type": "exhale", "duration": 6 }
    ]
  },
  {
    "id": "coherent",
    "name": "Coherent Breathing",
    "description": "Even 5-5 breathing that stabilizes the nervous system and mood.",
    "cycles": 10,
    "steps": [
      { "type": "inhale", "duration": 5 },
      { "type": "exhale", "duration": 5 }
    ]
  },
  {
    "id": "7_11",
    "name": "7-11 Anti-Anxiety Breathing",
    "description": "Longer exhales activate relaxation and reduce stress quickly.",
    "cycles": 5,
    "steps": [
      { "type": "inhale", "duration": 7 },
      { "type": "exhale", "duration": 11 }
    ]
  },
  {
    "id": "5_5_relaxation",
    "name": "5-5 Relaxation Breathing",
    "description": "Simple balanced breathing ideal for beginners.",
    "cycles": 6,
    "steps": [
      { "type": "inhale", "duration": 5 },
      { "type": "exhale", "duration": 5 }
    ]
  },
  {
    "id": "equal_breathing",
    "name": "Equal Breathing",
    "description": "Matching inhale and exhale for steady calm and clarity.",
    "cycles": 8,
    "steps": [
      { "type": "inhale", "duration": 4 },
      { "type": "exhale", "duration": 4 }
    ]
  },
  {
    "id": "energizing",
    "name": "Energizing Breath",
    "description": "Short, sharp exhales for alertness and energy boost.",
    "cycles": 6,
    "steps": [
      { "type": "inhale", "duration": 2 },
      { "type": "exhale", "duration": 1 }
    ]
  },
  {
    "id": "calm_long_exhale",
    "name": "Calming Long Exhale",
    "description": "A longer exhale pattern that deeply relaxes the body.",
    "cycles": 6,
    "steps": [
      { "type": "inhale", "duration": 4 },
      { "type": "exhale", "duration": 6 }
    ]
  },
  {
    "id": "morning_wakeup",
    "name": "Morning Wake-Up Breath",
    "description": "A gentle pattern to activate the body and mind in the morning.",
    "cycles": 5,
    "steps": [
      { "type": "inhale", "duration": 3 },
      { "type": "hold",   "duration": 1 },
      { "type": "exhale", "duration": 3 }
    ]
  }
]

""".trimIndent()

    override suspend fun fetchTrainings(): List<TrainingDto> {
        return Json.decodeFromString(trainingsJson)
    }
}
