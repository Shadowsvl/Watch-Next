package com.heka.watchnext.data.mapper

import com.google.gson.Gson
import com.heka.web_helper_kt.HttpError
import com.heka.web_helper_kt.ServiceResponse
import retrofit2.Response

fun <T> Response<T>.toServiceResponse(): ServiceResponse<T, Nothing> {

    if (isSuccessful) return ServiceResponse.Ok(data = body())

    return ServiceResponse.Error(
        code = code(),
        httpError = HttpError.fromCode(code()),
        errorMessage = message()
    )

}

fun <T,W> Response<T>.toServiceResponse(errorDtoClass: Class<W>): ServiceResponse<T, W> {

    if (isSuccessful) return ServiceResponse.Ok(data = body())

    return when(code()) {
        in 400..409 -> {
            val gson = Gson()
            try {
                ServiceResponse.Error(
                    errorDto = gson.fromJson(errorBody()?.string(), errorDtoClass),
                    code = code(),
                    httpError = HttpError.fromCode(code()),
                    errorMessage = message()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                ServiceResponse.Error(
                    code = code(),
                    httpError = HttpError.fromCode(code()),
                    errorMessage = message()
                )
            }
        }
        else -> ServiceResponse.Error(
            code = code(),
            httpError = HttpError.fromCode(code()),
            errorMessage = message()
        )
    }

}