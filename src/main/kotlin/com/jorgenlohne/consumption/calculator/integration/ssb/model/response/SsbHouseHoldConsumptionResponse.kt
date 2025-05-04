package com.jorgenlohne.consumption.calculator.integration.ssb.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SsbHouseHoldConsumptionResponse(
    val value: List<Int>,
)
