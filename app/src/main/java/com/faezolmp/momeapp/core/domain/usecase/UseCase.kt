package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.data.Resource
import com.faezolmp.momeapp.core.domain.model.ExampleModel

interface UseCase {
    fun exampleFunction() : Resource<ExampleModel>
}