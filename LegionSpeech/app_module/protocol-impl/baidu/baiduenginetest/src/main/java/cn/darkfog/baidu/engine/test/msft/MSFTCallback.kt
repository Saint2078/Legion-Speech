package com.bmwgroup.apinext.msft_lib

import cn.darkfog.dialog_manager.msft.NLUResult
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse

interface MSFTCallback {
    fun onResult(result: MSFTResponse)

    fun onError(throwable: Throwable, rawResult: NLUResult?)
}