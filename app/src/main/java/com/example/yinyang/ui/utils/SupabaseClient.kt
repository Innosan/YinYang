package com.example.yinyang.ui.utils

import android.content.Context
import android.widget.Toast
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest

val client = createSupabaseClient(
    supabaseUrl = "https://liskfjzxdlaenoukvmer.supabase.co",
    supabaseKey =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxpc2tmanp" +
            "4ZGxhZW5vdWt2bWVyIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODA5NDE2NzgsImV4cCI6MTk5NjUxNzY" +
            "3OH0.0QqDcjanSHr4T3Rtk2APYryyGkgDlkkQRs5xCn18bcI"
) {
    install(Postgrest) {
        // settings
    }

    install(GoTrue) {
        // settings
    }
}

/**
 * To perform multiple actions with user data, for example sign in up a new user or modifying.
 *
 * @param actionType type of user action, for example signUp or signIn
 * @param userEmail user's email for registration or sign in
 * @param userPassword user's password for registration or sign in
 * @param context used for displaying [Toast] messages
 */
suspend fun performUserAction(actionType: UserActions, userEmail: String, userPassword: String, context: Context) {
    val successfulActionMessage =
        if (actionType == UserActions.SIGNUP) {
            "Вы успешно зарегистрировались!"
        }
        else {
            "Вы успешно вошли!"
        }

    try {
        if (actionType == UserActions.SIGNUP) {
            client.gotrue.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }
        }
        else {
            client.gotrue.loginWith(Email) {
                email = userEmail
                password = userPassword
            }
        }

        Toast.makeText(context, successfulActionMessage, Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }
}