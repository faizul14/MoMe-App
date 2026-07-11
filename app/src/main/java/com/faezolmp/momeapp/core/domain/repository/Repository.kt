package com.faezolmp.momeapp.core.domain.repository

import com.faezolmp.momeapp.core.data.Resource
import com.faezolmp.momeapp.core.domain.model.ExampleModel

interface Repository {
    fun invoke() : Resource<ExampleModel>
}