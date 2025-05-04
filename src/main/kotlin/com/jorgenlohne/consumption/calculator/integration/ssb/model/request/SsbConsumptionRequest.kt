package com.jorgenlohne.consumption.calculator.integration.ssb.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SsbConsumptionRequest(
    val query: List<Query>,
    val response: Response,
)

@Serializable
data class Response(
    val format: String,
)

@Serializable
data class Query(
    val code: String,
    val selection: Selection,
)

@Serializable
data class Selection(
    val filter: String,
    val values: List<String>,
)