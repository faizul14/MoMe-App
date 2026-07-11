package com.faezolmp.projek_android_template.core.data

import com.faezolmp.projek_android_template.core.domain.model.ExampleModel
import com.faezolmp.projek_android_template.core.domain.repository.Repository
import com.faezolmp.projek_android_template.core.utils.DataMapper

class ImplRepository : Repository {
    override fun invoke(): Resource<ExampleModel> {
        return Resource.Success(DataMapper.mapperExampleModelFromLayerDataToLayerDomain("Example data"))
    }
}