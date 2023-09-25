package com.example.yinyang.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

val client = createSupabaseClient(
    supabaseUrl = "https://yfwjdrfyiqluljyiocqd.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inlmd2pkcmZ5aXFsdWxqeWlvY3FkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODQ1MjU3NzUsImV4cCI6MjAwMDEwMTc3NX0.DAu-F5_-IhVAtf5LwNG2fWBrY5hT7yD4etRExeHfwyQ"
) {
    install(Postgrest) {
        // settings
    }

    install(GoTrue) {
        // settings
    }

    install(Functions) {
        // settings
    }
}