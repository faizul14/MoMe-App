package com.faezolmp.projek_android_template.presentation.screen.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faezolmp.projek_android_template.core.data.Resource
import com.faezolmp.projek_android_template.core.domain.model.ExampleModel
import com.faezolmp.projek_android_template.core.domain.usecase.UseCase

class HomeViewModel(val useCase: UseCase): ViewModel(){
    private val _data = MutableLiveData<Resource<ExampleModel>>()
    val data: LiveData<Resource<ExampleModel>>
        get() = _data

    init {
        _data.value = useCase.exampleFunction()
    }
}