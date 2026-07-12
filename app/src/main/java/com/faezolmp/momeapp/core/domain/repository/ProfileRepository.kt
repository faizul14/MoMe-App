package com.faezolmp.momeapp.core.domain.repository

import com.faezolmp.momeapp.core.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun get(): Flow<Profile?>
    suspend fun save(profile: Profile)
}
