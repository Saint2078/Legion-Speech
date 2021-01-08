//package com.bmwgroup.apinext.msft_lib.net.interceptors
//
//import okhttp3.FormBody
//import okhttp3.Interceptor
//import okhttp3.Response
//
//class BodyInterceptor : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        var request = chain.request()
//        val body = request.body()
//        if (body is FormBody) {
//            val builder = FormBody.Builder()
//            body.size().takeIf { it > 0 }?.run {
//                for (i in 0 until body.size()) {
//                    builder.add(body.encodedName(i), body.encodedValue(i))
//                }
//            }
//            builder.add("senderId", "2G8f23klQ2045iH")
//            builder.add("senderNickname", "TestUser")
//            builder.add("msgId", "f5ff4f16fb90d07eb9475b5d9b582967")
//            builder.add("timestamp", "${System.currentTimeMillis() / 1000}")
//            request = request.newBuilder().post(builder.build()).build()
//        }
//        return chain.proceed(request)
//    }
//}