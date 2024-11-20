package com.b1nd.dodam.data.upload.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.upload.UploadRepository
import com.b1nd.dodam.data.upload.model.UploadModel
import com.b1nd.dodam.data.upload.model.toModel
import com.b1nd.dodam.network.upload.datasource.UploadDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UploadRepositoryImpl(
    private val network: UploadDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : UploadRepository {
    override suspend fun upload(fileName: String, fileMimeType: String, byteArray: ByteArray): Flow<Result<UploadModel>> {
        return flow {
            emit(
                network.upload(
                    fileName = fileName,
                    fileMimeType = fileMimeType,
                    byteArray = byteArray,
                ).toModel(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
