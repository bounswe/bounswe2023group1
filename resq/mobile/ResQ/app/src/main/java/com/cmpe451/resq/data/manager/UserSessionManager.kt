package com.cmpe451.resq.data.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserSessionManager(appContext: Context) {
    companion object {
        private const val PREF_NAME = "UserSessionPref"
        private const val JWT_TOKEN = "JWT_TOKEN"
        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
        private const val USER_SURNAME = "USER_SURNAME"
        private const val USER_EMAIL = "USER_EMAIL"
        private const val USER_ROLES = "USER_ROLES"
        private const val SELECTED_ROLE = "SELECTED_ROLE"
        private const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"

        @Volatile
        private lateinit var instance: UserSessionManager
        fun getInstance(appContext: Context): UserSessionManager {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = UserSessionManager(appContext)
                }
                return instance
            }
        }
    }

    private var pref: SharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession(token: String, userId: Int, userName: String, userSurname: String, userEmail: String, userRoles: List<String>) {
        val gson = Gson()
        val rolesJson = gson.toJson(userRoles)

        editor.putString(JWT_TOKEN, token)
        editor.putInt(USER_ID, userId)
        editor.putString(USER_NAME, userName)
        editor.putString(USER_SURNAME, userSurname)
        editor.putString(USER_EMAIL, userEmail)
        editor.putString(USER_ROLES, rolesJson)
        if (userRoles.isNotEmpty()) {
            editor.putString(SELECTED_ROLE, userRoles.last())
        }
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUserToken(): String? = pref.getString(JWT_TOKEN, null)

    fun getUserId(): Int = pref.getInt(USER_ID, -1)

    fun getUserName(): String? = pref.getString(USER_NAME, null)

    fun getUserSurname(): String? = pref.getString(USER_SURNAME, null)

    fun getUserEmail(): String? = pref.getString(USER_EMAIL, null)

    fun getUserRoles(): List<String> {
        val gson = Gson()
        val rolesJson = pref.getString(USER_ROLES, null) ?: return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(rolesJson, type)
    }

    fun getSelectedRole(): String? = pref.getString(SELECTED_ROLE, null)

    fun setSelectedRole(role: String) {
        editor.putString(SELECTED_ROLE, role)
        editor.apply()
    }
    fun isLoggedIn(): Boolean = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
