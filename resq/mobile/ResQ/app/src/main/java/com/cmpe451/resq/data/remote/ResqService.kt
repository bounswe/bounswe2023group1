package com.cmpe451.resq.data.remote

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.cmpe451.resq.data.Constants
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.CreateNeedRequestBody
import com.cmpe451.resq.data.models.CreateResourceRequestBody
import com.cmpe451.resq.data.models.LoginRequestBody
import com.cmpe451.resq.data.models.LoginResponse
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.models.NotificationItem
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.data.models.RegisterRequestBody
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.data.models.Task
import com.cmpe451.resq.data.models.UserInfo
import com.cmpe451.resq.data.models.UserInfoRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File


interface CategoryTreeNodeService {
    @GET("categorytreenode/getMainCategories")
    suspend fun getMainCategories(
        @Header("Authorization") jwtToken: String
    ): Response<List<CategoryTreeNode>>
}

interface ResourceService {
    @Multipart
    @POST("resource/createResource")
    suspend fun createResource(
        @Header("Authorization") jwtToken: String,
        @Part createResourceRequest: MultipartBody.Part,
        @Part file: MultipartBody.Part?
    ): Response<Int>

    @GET("resource/filterByDistance")
    fun filterResourceByDistance(
        @Header("Authorization") jwtToken: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Double
    ): Call<List<Resource>>

    @GET("resource/filterByCategory")
    fun filterResourceByCategory(
        @Header("Authorization") jwtToken: String,
        @Query("categoryTreeId") categoryTreeId: String?,
        @Query("longitude") longitude: Double?,
        @Query("latitude") latitude: Double?,
        @Query("userId") userId: Int?,
        @Query("status") status: String?,
        @Query("receiverId") receiverId: Long?
    ): Call<List<Resource>>
}

interface NeedService {
    @POST("need/createNeed")
    suspend fun createNeed(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Body requestBody: CreateNeedRequestBody
    ): Response<Int>

    @GET("need/filterByDistance")
    fun filterNeedByDistance(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Double,
        @Header("Authorization") jwtToken: String
    ): Call<List<Need>>

    @GET("need/viewNeedsByUserId")
    fun viewNeedsByUserId(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
    ): Call<List<Need>>

    @GET("need/viewAllNeeds")
    fun viewAllNeeds(
        @Header("Authorization") jwtToken: String
    ): Call<List<Need>>

    @POST("need/deleteNeed")
    fun deleteNeed(
        @Query("userId") userId: Int,
        @Query("needId") needId: Int,
        @Header("Authorization") jwtToken: String
    ): Call<ResponseBody>
}

interface AuthService {
    @POST("auth/signin")
    suspend fun login(@Body requestBody: LoginRequestBody):  Response<LoginResponse>

    @POST("auth/signup")
    suspend fun register(@Body requestBody: RegisterRequestBody): Response<ResponseBody>
}

interface ProfileService {
    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String
    ): Response<ProfileData>

    @POST("profile/updateProfile")
    suspend fun updateProfile(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Body request: UserInfoRequest
    ): Response<String>

    @POST("user/requestRole")
    suspend fun selectRole(
        @Query("userId") userId: Int,
        @Query("role") requestedRole: String,
        @Header("Authorization") jwtToken: String
    ): Response<String>

    @GET("user/getUserInfo")
    fun getUserInfo(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String
    ): Call<UserInfo>

}

interface NotificationService {
    @GET("notification/viewAllNotifications")
    suspend fun getNotifications(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String
    ):  Response<List<NotificationItem>>
}

interface TaskService {
    @GET("task/viewTasks")
     fun viewTasks(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String
    ):  Call<List<Task>>

    @POST("task/viewTaskByFilter")
    fun viewTasksByFilter(
        @Query("assignerId") assignerId: Int?,
        @Query("assigneeId") assigneeId: Int?,
        @Query("urgency") urgency: String?,
        @Query("status") status: String?,
        @Header("Authorization") jwtToken: String
    ):  Call<List<Task>>
}

class ResqService(appContext: Context) {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BACKEND_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val categoryTreeNodeService: CategoryTreeNodeService = retrofit.create(CategoryTreeNodeService::class.java)
    private val resourceService: ResourceService = retrofit.create(ResourceService::class.java)
    private val needService: NeedService = retrofit.create(NeedService::class.java)
    private val authService: AuthService = retrofit.create(AuthService::class.java)
    private val profileService: ProfileService = retrofit.create(ProfileService::class.java)
    private val notificationService: NotificationService = retrofit.create(NotificationService::class.java)
    private val taskService: TaskService = retrofit.create(TaskService::class.java)
    private val userSessionManager: UserSessionManager = UserSessionManager.getInstance(appContext)

    // Category Tree Node methods
    suspend fun getMainCategories(): Response<List<CategoryTreeNode>> {
        val token = userSessionManager.getUserToken() ?: ""

        return categoryTreeNodeService.getMainCategories(
            jwtToken = "Bearer $token"
        )
    }

    // Resource methods
    suspend fun createResource(createResourceRequestBody: CreateResourceRequestBody, file: File?): Response<Int> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""

        // Convert CreateResourceRequestBody to JSON and then to RequestBody
        val requestJson = Gson().toJson(createResourceRequestBody.copy(senderId = userId))
        val requestJsonBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestJson)
        val jsonPart = MultipartBody.Part.createFormData("createResourceRequest", null, requestJsonBody)

        // Handle the file part being nullable
        val filePart = file?.let {
            val fileRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("file", it.name, fileRequestBody)
        }

        // Make the API call
        return resourceService.createResource(
            jwtToken = "Bearer $token",
            createResourceRequest = jsonPart,
            file = filePart // This can be null
        )
    }




    // Need methods
    suspend fun createNeed(request: CreateNeedRequestBody): Response<Int> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""

        return needService.createNeed(
            userId = userId,
            jwtToken = "Bearer $token",
            requestBody = request
        )
    }

    fun filterNeedByDistance(
        latitude: Double,
        longitude: Double,
        distance: Double,
        onSuccess: (List<Need>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        needService.filterNeedByDistance(latitude, longitude, distance, "Bearer $token").enqueue(object :
            Callback<List<Need>> {
            override fun onResponse(call: Call<List<Need>>, response: Response<List<Need>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Need>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getAllNeeds(
        onSuccess: (List<Need>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        needService.viewAllNeeds("Bearer $token").enqueue(object :
            Callback<List<Need>> {
            override fun onResponse(call: Call<List<Need>>, response: Response<List<Need>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Need>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun filterResourceByDistance(
        latitude: Double,
        longitude: Double,
        distance: Double,
        onSuccess: (List<Resource>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        resourceService.filterResourceByDistance("Bearer $token", latitude, longitude, distance).enqueue(object :
            Callback<List<Resource>> {
            override fun onResponse(call: Call<List<Resource>>, response: Response<List<Resource>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Resource>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun filterResourceByCategory(
        categoryTreeId: String?,
        longitude: Double?,
        latitude: Double?,
        userId: Long?,
        status: String?,
        receiverId: Long?,
        onSuccess: (List<Resource>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        resourceService.filterResourceByCategory(
            "Bearer $token",
            null,
            null,
            null,
            null,
            null,
            null).enqueue(object :
            Callback<List<Resource>> {
            override fun onResponse(call: Call<List<Resource>>, response: Response<List<Resource>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Resource>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun filterResourceByCategory(
        onSuccess: (List<Resource>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        val userID = userSessionManager.getUserId() ?: 0
        resourceService.filterResourceByCategory(
            "Bearer $token",
            null,
            null,
            null,
            userID,
            null,
            null).enqueue(object :
            Callback<List<Resource>> {
            override fun onResponse(call: Call<List<Resource>>, response: Response<List<Resource>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Resource>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun viewNeedsByUserId(
        onSuccess: (List<Need>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()
        needService.viewNeedsByUserId(userId = userId, "Bearer $token").enqueue(object :
            Callback<List<Need>> {
            override fun onResponse(call: Call<List<Need>>, response: Response<List<Need>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError(RuntimeException("Response not successful"))
                }
            }
            override fun onFailure(call: Call<List<Need>>, t: Throwable) {
                onError(t)
            }
        })
    }
    fun deleteNeed(
        needId: Int,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken()
        val userId = userSessionManager.getUserId()
        needService.deleteNeed(userId = userId, needId = needId, "Bearer $token").enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBodyString = response.body()?.string() ?: ""
                    onSuccess(responseBodyString)
                } else {
                    val errorBodyString = response.errorBody()?.string() ?: "Error without a message"
                    onError(RuntimeException("Response not successful: HTTP ${response.code()} - $errorBodyString"))
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t)
            }
        })
    }

    // Auth methods
    suspend fun login(request: LoginRequestBody): Response<LoginResponse> = authService.login(request)
    suspend fun register(request: RegisterRequestBody): Response<ResponseBody> = authService.register(request)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getProfileInfo(): ProfileData {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()

        val response = profileService.getProfileInfo(
            userId = userId,
            jwtToken = "Bearer $token"
        )

        return ProfileData(
            name = response.body()?.name,
            surname = response.body()?.surname,
            city = response.body()?.city,
            country = response.body()?.country,
            gender = response.body()?.gender,
            bloodType = response.body()?.bloodType,
            height = response.body()?.height,
            weight = response.body()?.weight,
            phoneNumber = response.body()?.phoneNumber,
            state = response.body()?.state,
            emailConfirmed = response.body()?.emailConfirmed,
            privacyPolicyAccepted = response.body()?.privacyPolicyAccepted,
            birth_date = response.body()?.birth_date,
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateUserData(profileData: ProfileData): Response<String> {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()
        val request = UserInfoRequest(
            name = profileData.name ?: "",
            surname = profileData.surname ?: "",
            birth_date = profileData.birth_date ?: null,
            country = profileData.country ?: "",
            city = profileData.city ?: "",
            state = profileData.state ?: "",
            bloodType = profileData.bloodType ?: "",
            height = profileData.height,
            weight = profileData.weight,
            gender = profileData.gender ?: "",
            phoneNumber = profileData.phoneNumber ?: "",
            emailConfirmed = true,
            privacyPolicyAccepted = true
        )
        return profileService.updateProfile(
            userId = userId,
            jwtToken = "Bearer $token",
            request = request
        )
    }

    suspend fun selectRole(requestedRole: String): Response<String> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""
        val response = profileService.selectRole(
            userId = userId,
            requestedRole = requestedRole,
            jwtToken = "Bearer $token"
        )

        return response
    }

    suspend fun getNotifications(): Response<List<NotificationItem>> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""
        val response = notificationService.getNotifications(
            userId = userId,
            jwtToken = "Bearer $token",
        )
        Log.d("AAA", "getNotifications: ${response.isSuccessful}")
        return response
    }
    fun getUserInfo(userId: Int, callback: (UserInfo?) -> Unit) {
        val token = userSessionManager.getUserToken() ?: ""
        profileService.getUserInfo(userId, "Bearer $token").enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                callback(null)
            }
        })
    }
    fun viewMyTasks(
        onSuccess: (List<Task>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()

        taskService.viewTasks(userId = userId, jwtToken = "Bearer $token").enqueue(object :
            Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    }
                } else {
                    val error = RuntimeException("Response not successful: ${response.errorBody()?.string()}")
                    onError(error)
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                onError(t)
            }
        })
    }


    fun getTasks(
        onSuccess: (List<Task>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val token = userSessionManager.getUserToken() ?: ""

        taskService.viewTasksByFilter(assigneeId = null, assignerId = null, urgency = "", status = "", jwtToken = "Bearer $token").enqueue(object :
            Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    }
                } else {
                    val error = RuntimeException("Response not successful: ${response.errorBody()?.string()}")
                    onError(error)
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                onError(t)
            }
        })
    }
}

