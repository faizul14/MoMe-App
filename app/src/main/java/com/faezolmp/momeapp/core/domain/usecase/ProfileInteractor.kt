package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.domain.model.Profile
import com.faezolmp.momeapp.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileInteractor(private val repository: ProfileRepository) : ProfileUseCase {
    override fun get(): Flow<Profile?> = repository.get()
    override suspend fun save(profile: Profile) = repository.save(profile)
}
