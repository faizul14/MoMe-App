package com.faezolmp.momeapp.core.data.repository

import com.faezolmp.momeapp.core.data.local.dao.ProfileDao
import com.faezolmp.momeapp.core.data.local.entity.ProfileEntity
import com.faezolmp.momeapp.core.domain.model.Profile
import com.faezolmp.momeapp.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(private val dao: ProfileDao) : ProfileRepository {

    override fun get(): Flow<Profile?> =
        dao.getProfile().map { entity -> entity?.let { Profile(it.name, it.email) } }

    override suspend fun save(profile: Profile) =
        dao.upsert(ProfileEntity(id = 1L, name = profile.name, email = profile.email))
}
