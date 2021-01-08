package com.bmwgroup.apinext.msft_lib.model.net.interceptors

import com.bmwgroup.apinext.msft_lib.MSFTConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val key: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newReq = request.newBuilder()
            .addHeader("subscription-key", key)
            .addHeader("timestamp", "${System.currentTimeMillis() / 1000}")
            .addHeader("Content-Type", "application/json; charset=utf8")
            .addHeader("signature", MSFTConfig.SIGNATURE_KEY)
            .build()
        return chain.proceed(newReq)
    }

    /**
     * useful,do not delete it
     */
//    private fun calculateSignature(body: String, privateKey: String, timestamp: String): String {
//        val md = MessageDigest.getInstance("SHA-512")
//        val stringToCheckSum = body + privateKey + timestamp
//        md.update(stringToCheckSum.toByteArray(StandardCharsets.UTF_8))
//        val hex = Hex.encodeHexString(md.digest())
//        return hex.replace("-", "")
//    }
}