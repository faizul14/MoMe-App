package com.faezolmp.projek_android_template.core.domain.usecase

import com.faezolmp.projek_android_template.core.data.Resource
import com.faezolmp.projek_android_template.core.domain.model.ExampleModel
import com.faezolmp.projek_android_template.core.domain.repository.Repository

class UseCaseIteractor(val repository: Repository) : UseCase {
    override fun exampleFunction(): Resource<ExampleModel> {
        return repository.invoke()
    }
}