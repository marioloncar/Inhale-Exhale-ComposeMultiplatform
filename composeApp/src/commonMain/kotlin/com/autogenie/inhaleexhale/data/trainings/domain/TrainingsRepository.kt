package com.autogenie.inhaleexhale.data.trainings.domain

import com.autogenie.inhaleexhale.data.trainings.domain.model.Category
import com.autogenie.inhaleexhale.data.trainings.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {

    fun trainings(): Flow<List<Training>>

    fun training(id: String): Flow<Training?>

    fun categories(): Flow<List<Category>>
}
