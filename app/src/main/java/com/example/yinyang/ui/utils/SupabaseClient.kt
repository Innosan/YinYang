package com.example.yinyang.ui.utils

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
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