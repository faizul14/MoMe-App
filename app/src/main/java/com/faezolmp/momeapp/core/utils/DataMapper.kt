package com.faezolmp.momeapp.core.utils

import com.faezolmp.momeapp.core.domain.model.ExampleModel

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