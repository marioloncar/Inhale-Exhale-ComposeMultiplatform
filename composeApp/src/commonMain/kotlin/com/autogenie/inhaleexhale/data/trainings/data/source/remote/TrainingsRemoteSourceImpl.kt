package com.autogenie.inhaleexhale.data.trainings.data.source.remote

import com.autogenie.inhaleexhale.data.trainings.data.source.remote.model.TrainingDto
import kotlinx.serialization.json.Json

class TrainingsRemoteSourceImpl : TrainingsRemoteSource {

    val trainingsJson = """
[
  {
    "id": "box",
    "name": "Box Breathing",
    "summary": "A steady, balanced 4-4-4-4 pattern.",
    "description": "Box breathing uses an even 4-4-4-4 pattern to create balance in the nervous system. It helps sharpen concentration, reduce stress, and bring the mind back into a stable rhythm. This technique is widely used for grounding and quick mental reset.",
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
    "summary": "A slow pattern for deep calm.",
    "description": "The 4-7-8 technique is designed to slow the heart rate and signal deep relaxation. By extending the hold and exhale, it naturally quiets the nervous system and helps ease anxious thoughts. It’s ideal for transitioning into sleep or unwinding after stress.",
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
    "summary": "A fast reset for sudden stress.",
    "description": "The physiological sigh uses a double inhale followed by an extended exhale to quickly release tension from the body. This method naturally balances carbon dioxide levels and promotes immediate calm. It’s one of the fastest ways to reduce acute stress.",
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
    "summary": "Even 5-5 breath for steady balance.",
    "description": "Coherent breathing uses slow, even 5-second inhales and exhales to synchronize the heart, lungs, and nervous system. This pattern promotes emotional balance, reduces stress, and supports long-term resilience. It’s a foundational breathing exercise for daily practice.",
    "cycles": 10,
    "steps": [
      { "type": "inhale", "duration": 5 },
      { "type": "exhale", "duration": 5 }
    ]
  },
  {
    "id": "7_11",
    "name": "7-11 Anti-Anxiety Breathing",
    "summary": "Long exhales for deep relaxation.",
    "description": "The 7-11 breathing method emphasizes longer exhales to switch the body into a calmer parasympathetic state. It helps slow down racing thoughts, reduce anxiety, and restore a sense of ease. This pattern works especially well during moments of emotional overwhelm.",
    "cycles": 5,
    "steps": [
      { "type": "inhale", "duration": 7 },
      { "type": "exhale", "duration": 11 }
    ]
  },
  {
    "id": "energizing",
    "name": "Energizing Breath",
    "summary": "Short exhales for a quick energy boost.",
    "description": "The energizing breath features fast exhales and short inhales to wake up the body. It increases alertness, stimulates circulation, and helps break through sluggish moments. Use it when you need a natural lift without caffeine.",
    "cycles": 6,
    "steps": [
      { "type": "inhale", "duration": 2 },
      { "type": "exhale", "duration": 1 }
    ]
  },
  {
    "id": "calm_long_exhale",
    "name": "Calming Long Exhale",
    "summary": "Extended exhales for full-body calm.",
    "description": "This technique slows the breathing rhythm by extending the exhale, which signals the body to unwind. Longer exhales release tension, lower stress hormones, and support a calm mental state. It’s ideal for ending the day or easing into rest.",
    "cycles": 6,
    "steps": [
      { "type": "inhale", "duration": 4 },
      { "type": "exhale", "duration": 6 }
    ]
  },
  {
    "id": "morning_wakeup",
    "name": "Morning Wake-Up Breath",
    "summary": "A light pattern to start the day.",
    "description": "The morning wake-up breath uses a short inhale-hold-exhale rhythm to energize the mind while keeping the body calm. It encourages oxygen flow and promotes alertness without overstimulation. A great way to start the day with clarity and intention.",
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
