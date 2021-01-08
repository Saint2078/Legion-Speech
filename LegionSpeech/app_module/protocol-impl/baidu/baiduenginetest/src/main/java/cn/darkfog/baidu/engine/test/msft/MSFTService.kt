package com.bmwgroup.apinext.msft_lib

import com.bmwgroup.apinext.msft_lib.model.bean.MSFTRequestBody
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface MSFTService {

    @POST(MSFTConfig.PATH)
    fun call(
        @HeaderMap headers: Map<String, String>,
        @Body body: MSFTRequestBody
    ): Call<MSFTResponse>

}
