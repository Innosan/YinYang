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

suspend fun signUp(userEmail: String, userPassword: String, context: Context) {
    try {
        client.gotrue.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }

        Toast.makeText(context, "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }
}

suspend fun signIn(userEmail: String, userPassword: String, context: Context) {
    try {
        client.gotrue.loginWith(Email) {
            email = userEmail
            password = userPassword
        }

        Toast.makeText(context, "Вы успешно вошли!", Toast.LENGTH_SHORT).show()
    } catch (error: Exception) {
        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
    }
}