package com.faezolmp.projek_android_template.core.utils

import com.faezolmp.projek_android_template.core.domain.model.ExampleModel

object DataMapper {
    fun mapperExampleModelFromLayerDataToLayerDomain(data: String): ExampleModel {
        return ExampleModel(
            dataExample = data
        )
    }

    fun sortMapper(data: String) = ExampleModel(
        dataExample = data
    )
}