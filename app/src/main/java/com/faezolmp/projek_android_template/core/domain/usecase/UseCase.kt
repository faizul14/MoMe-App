package com.faezolmp.projek_android_template.core.domain.usecase

import com.faezolmp.projek_android_template.core.data.Resource
import com.faezolmp.projek_android_template.core.domain.model.ExampleModel

interface UseCase {
    fun exampleFunction() : Resource<ExampleModel>
}