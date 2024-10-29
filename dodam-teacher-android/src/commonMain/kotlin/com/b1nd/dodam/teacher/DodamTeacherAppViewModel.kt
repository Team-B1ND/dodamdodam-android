package com.b1nd.dodam.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.bundleidinfo.BundleIdInfoRepository
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.teacher.model.BundleIdInfoModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class DodamTeacherAppViewModel : ViewModel(), KoinComponent {

    private val dataStoreRepository: DataStoreRepository by inject()
    private val bundleIdInfoRepository: BundleIdInfoRepository by inject()

    private val _isLoginState = MutableStateFlow<Boolean?>(null)
    val isLoginState = _isLoginState.asStateFlow()
    private val _bundleModel = MutableStateFlow(BundleIdInfoModel())
    val bundleModel = _bundleModel.asStateFlow()

    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))

    fun loadToken() = viewModelScope.launch {
        _isLoginState.value = dataStoreRepository.token.first().isNotEmpty()
    }

    fun getBundleId(){
        viewModelScope.launch(dispatcher) {
            bundleIdInfoRepository.getBundleId().collect{
                when(it){
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _bundleModel.update { model ->
                            model.copy(
                                bundleId = it.data
                            )
                        }
                    }
                }
            }
        }
    }
}
