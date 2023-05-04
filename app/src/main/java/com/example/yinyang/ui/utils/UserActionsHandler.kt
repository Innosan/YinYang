package com.example.yinyang.ui.utils

import android.content.Context
import android.widget.Toast
import com.example.yinyang.ui.shared.models.User
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest

/**
 * A class for handling user actions such as sign-up and log-in.
 * @param context The context in which to display Toast messages.
 */
class UserActionsHandler(private val context: Context) {

    /**
     * Sign up a new user.
     * @param userEmail The user's email address.
     * @param userPassword The user's password.
     */
    private suspend fun signUp(userEmail: String, userPassword: String) {
        client.gotrue.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }

        val newUserInfo: UserInfo = client.gotrue.retrieveUserForCurrentSession()
        createUser(newUserInfo.id)
    }

    /**
     * Log in an existing user.
     * @param userEmail The user's email address.
     * @param userPassword The user's password.
     */
    private suspend fun logIn(userEmail: String, userPassword: String) {
        client.gotrue.loginWith(Email) {
            email = userEmail
            password = userPassword
        }
    }

    private suspend fun logOut() {
        client.gotrue.invalidateSession()
    }

    /**
     * Perform a user action.
     * @param userAction The user action to perform.
     * @param userEmail The user's email address.
     * @param userPassword The user's password.
     */
    suspend fun performUserAction(userAction: UserAction, userEmail: String = "", userPassword: String = "") {
        val successfulActionMessage = when (userAction) {
            UserAction.SIGNUP -> "Вы успешно зарегистрировались!"
            UserAction.LOGIN -> "Вы успешно вошли!"
            UserAction.LOGOUT -> "Вы успешно вышли из аккаунта!"
            //UserAction.UPDATE -> "Информация обновлена!"
        }

        try {
            when (userAction) {
                UserAction.SIGNUP -> signUp(userEmail, userPassword)
                UserAction.LOGIN -> logIn(userEmail, userPassword)
                UserAction.LOGOUT -> logOut()
            }

            Toast.makeText(context, successfulActionMessage, Toast.LENGTH_SHORT).show()
        }
        catch (error: Exception) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Create a new user in the PostgREST database.
     * @param uuid The user's UUID.
     */
    private suspend fun createUser(uuid: String) {
        val user = User(
            null,
            null,
            null,
            "Change",
            "Me",
            "sample_url",
            uuid,
            1
        )

        try {
            client.postgrest["user"]
                .insert(user)
        } catch (error: Exception) {
            println(error.message)
        }
    }
}