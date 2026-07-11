package com.faezolmp.projek_android_template.core.domain.repository

import com.faezolmp.projek_android_template.core.data.Resource
import com.faezolmp.projek_android_template.core.domain.model.ExampleModel

interface Repository {
    fun invoke() : Resource<ExampleModel>
}