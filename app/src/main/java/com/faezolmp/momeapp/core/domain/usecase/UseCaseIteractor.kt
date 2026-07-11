package com.faezolmp.momeapp.core.domain.usecase

import com.faezolmp.momeapp.core.data.Resource
import com.faezolmp.momeapp.core.domain.model.ExampleModel
import com.faezolmp.momeapp.core.domain.repository.Repository

class UseCaseIteractor(val repository: Repository) : UseCase {
    override fun exampleFunction(): Resource<ExampleModel> {
        return repository.invoke()
    }
}