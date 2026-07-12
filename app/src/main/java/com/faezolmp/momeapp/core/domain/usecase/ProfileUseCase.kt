package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
    fun get(): Flow<Profile?>
    suspend fun save(profile: Profile)
}
