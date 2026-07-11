package com.faezolmp.momeapp.core.data

import com.faezolmp.momeapp.core.domain.model.ExampleModel
import com.faezolmp.momeapp.core.domain.repository.Repository
import com.faezolmp.momeapp.core.utils.DataMapper

class ImplRepository : Repository {
    override fun invoke(): Resource<ExampleModel> {
        return Resource.Success(DataMapper.mapperExampleModelFromLayerDataToLayerDomain("Example data"))
    }
}