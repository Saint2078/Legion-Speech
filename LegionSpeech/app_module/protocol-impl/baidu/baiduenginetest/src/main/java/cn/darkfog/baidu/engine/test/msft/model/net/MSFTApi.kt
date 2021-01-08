package cn.darkfog.dialog_manager.msft.model.net

import com.bmwgroup.apinext.msft_lib.MSFTConfig
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTRequestBody
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MSFTApi {

    @POST(MSFTConfig.PATH)
    fun requestReply(@Body msftBody: MSFTRequestBody): Call<MSFTResponse>
}