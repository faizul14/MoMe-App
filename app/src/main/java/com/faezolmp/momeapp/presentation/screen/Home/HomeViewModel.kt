package com.faezolmp.momeapp.presentation.screen.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faezolmp.momeapp.core.data.Resource
import com.faezolmp.momeapp.core.domain.model.ExampleModel
import com.faezolmp.momeapp.core.domain.usecase.UseCase

class HomeViewModel(val useCase: UseCase): ViewModel(){
    private val _data = MutableLiveData<Resource<ExampleModel>>()
    val data: LiveData<Resource<ExampleModel>>
        get() = _data

    init {
        _data.value = useCase.exampleFunction()
    }
}