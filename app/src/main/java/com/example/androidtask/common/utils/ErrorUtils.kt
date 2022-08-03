package com.example.androidtask.common.utils


import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


/**
 * helper class to parse errors thrown from retrofit
 */
object ErrorUtils {
        inline fun <reified T> parseError(t: Throwable?): T? {

            var errorResponse:T? = null
            when (t) {
                is HttpException -> {
                    t.response()?.errorBody()?.string()?.let {
                        errorResponse = Gson().fromJson(it, T::class.java)
                    }
//                    when (t.code()) {
//                        401 -> {
//                            errorResponse = ErrorResponseBody(401, error = t.message())
//                        }
//                    }
                }
//                is IOException, is UnknownHostException, is SocketTimeoutException -> {
//                    errorResponse = ErrorResponseBody<T>( "Check Internet Connection" as T,false)
//                }
            }
            return errorResponse
        }

    inline fun <reified T> parseError(response: Response<*>,retrofit: Retrofit): ErrorResponseBody<T>? {
        val converter: Converter<ResponseBody, ErrorResponseBody<T>> = retrofit
            .responseBodyConverter(ErrorResponseBody::class.java, arrayOfNulls<Annotation>(0))
        val error: ErrorResponseBody<T>? = try {
            response.errorBody()?.let { converter.convert(it) }
        } catch (e: IOException) {
            return ErrorResponseBody()
        }
        return error
    }
    }
