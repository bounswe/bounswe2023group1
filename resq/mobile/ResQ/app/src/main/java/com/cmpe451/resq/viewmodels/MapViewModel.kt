package com.cmpe451.resq.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.data.remote.ResqService
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    val searchQuery = mutableStateOf("")
    val lastKnownLocation = mutableStateOf<Location?>(null)
    val needMarkerList = mutableStateOf<List<Need>>(emptyList())
    val resourceMarkerList = mutableStateOf<List<Resource>>(emptyList())

    // For convert category ids to names
    private val _categories = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    val categories: State<List<CategoryTreeNode>> = _categories

    fun getNeedsByDistance(appContext: Context) {
        val api = ResqService(appContext)

        val latitude = UserSessionManager.getInstance(appContext).getLocation()?.latitude?: 41.086571
        val longitude = UserSessionManager.getInstance(appContext).getLocation()?.longitude?: 29.046109

        api.filterNeedByDistance(
            latitude = latitude,
            longitude = longitude,
            distance = 1000.0,
            onSuccess = { needList ->
                needMarkerList.value = needList
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    fun getAllNeeds(appContext: Context) {
        val api = ResqService(appContext)

        api.getAllNeeds(
            onSuccess = { needList ->
                needMarkerList.value = needList
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    fun getResourcesByDistance(appContext: Context) {
        val api = ResqService(appContext)

        api.filterResourceByDistance(
            latitude = 41.086571,
            longitude = 29.046109,
            distance = 1000.0,
            onSuccess = { resourceList ->
                resourceMarkerList.value = resourceList
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    fun getAllResources(appContext: Context) {
        val api = ResqService(appContext)

        api.filterResourceByCategory(
            categoryTreeId = null,
            longitude = null,
            latitude = null,
            userId = null,
            status = null,
            receiverId = null,
            onSuccess = { resourceListResponse ->
                resourceMarkerList.value = resourceListResponse
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lastKnownLocation.value = task.result
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    fun saveLastKnownLocation(appContext: Context) {
        val userSessionManager = UserSessionManager.getInstance(appContext)
        userSessionManager.saveLocation(lastKnownLocation.value)
    }

    fun fetchMainCategories(appContext: Context) {
        viewModelScope.launch {
            val api = ResqService(appContext)

            val response = api.getMainCategories()
            if (response.isSuccessful) {
                _categories.value = response.body() ?: emptyList()
            } else {
                // TODO: Handle error
            }
        }
    }

    fun findNodeById(rootNodes: List<CategoryTreeNode>, idToFind: Int): CategoryTreeNode? {
        for (node in rootNodes) {
            if (node.id == idToFind) {
                return node
            }
            val childResult = findNodeById(node.children, idToFind)
            if (childResult != null) {
                return childResult
            }
        }
        return null
    }

    fun getUserInfoById(appContext: Context, userId: Int, callback: (String) -> Unit) {
        val api = ResqService(appContext)
        api.getUserInfo(userId) { userInfo ->
            val username = userInfo?.let { "${it.name} ${it.surname}" } ?: "Unknown"
            callback(username)
        }
    }

}