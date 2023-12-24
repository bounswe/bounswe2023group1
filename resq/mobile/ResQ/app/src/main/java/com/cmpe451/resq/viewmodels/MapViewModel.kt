package com.cmpe451.resq.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.data.remote.ResqService
import com.google.android.gms.location.FusedLocationProviderClient

class MapViewModel : ViewModel() {
    val searchQuery = mutableStateOf("")
    val lastKnownLocation = mutableStateOf<Location?>(null)
    val needMarkerList = mutableStateOf<List<Need>>(emptyList())
    val resourceMarkerList = mutableStateOf<List<Resource>>(emptyList())

    fun getNeedsByDistance(appContext: Context) {
        val api = ResqService(appContext)
        api.filterNeedByDistance(
            latitude = 41.086571,
            longitude = 29.046109,
            distance = 1000.0,
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
}