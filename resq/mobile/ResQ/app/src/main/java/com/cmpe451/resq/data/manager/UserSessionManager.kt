import android.content.Context
import android.content.SharedPreferences

class UserSessionManager(context: Context) {
    companion object {
        private const val PREF_NAME = "UserSessionPref"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_TOKEN = "userToken"
        // Add other user-related keys
    }

    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession(token: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER_TOKEN, token)
        // Store other user info if needed
        editor.apply()
    }

    fun getUserToken(): String? = pref.getString(KEY_USER_TOKEN, null)

    fun isLoggedIn(): Boolean = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    // Implement logout or other session management functions
}
