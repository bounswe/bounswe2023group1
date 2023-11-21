package com.cmpe451.resq.data.remote

import com.cmpe451.resq.data.models.NotificationItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET


data class NotificationRequest(
    val userID: String,
    val password: String
)
interface NotificationService {
    @GET("notification/getUserNotifications")
    suspend fun getNotifications(@Body request: NotificationRequest):  Response<List<NotificationItem>>
}
class NotificationApi {

    private val notificationService: NotificationService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://16.16.63.194/resq/api/v1/") // TODO: Replace with backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        notificationService = retrofit.create(NotificationService::class.java)
    }


    suspend fun getNotifications(notificationRequest: NotificationRequest): Response<List<NotificationItem>> {
        return notificationService.getNotifications(notificationRequest)
    }

}
