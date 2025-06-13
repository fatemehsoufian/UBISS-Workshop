package org.uni.myapplication

data class SummaryAlertResponse(
    val type: String, // "summary"
    val messages: Map<String, String> // "success" and "fail" messages
)