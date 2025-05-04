package com.jorgenlohne.consumption.calculator.domain.model.response

data class CompareConsumptionResponse(
    val foodAndBeverages: Int?,
    val alcoholTobaccoNarcotics: Int?,
    val clothingAndFootwear: Int?,
    val housing: Int?,
    val housingAndUtilities: Int?,
    val furnishingsAndHouseholdEquipment: Int?,
    val health: Int?,
    val transport: Int?,
    val communication: Int?,
    val recreationAndCulture: Int?,
    val education: Int?,
    val restaurantsAndHotels: Int?,
    val insuranceAndFinancialServices: Int?,
    val personalCare: Int?,
)
