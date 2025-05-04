package com.jorgenlohne.consumption.calculator.domain.controller

import com.jorgenlohne.consumption.calculator.domain.model.request.HouseHoldCompareConsumptionRequest
import com.jorgenlohne.consumption.calculator.domain.model.response.CompareConsumptionResponse
import com.jorgenlohne.consumption.calculator.domain.service.HouseHoldConsumptionCalculatorService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v0/"])
class HouseHoldConsumptionCalculatorController(private val houseHoldConsumptionCalculatorService: HouseHoldConsumptionCalculatorService) {

    /**
     * Endpoint to compare consumption with the average consumption in Norway.
     * @return ResponseEntity with the consumption stats
     */
    @PostMapping(
        path = ["compareConsumption"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getConsumption(
        @RequestBody request: HouseHoldCompareConsumptionRequest,
    ): ResponseEntity<CompareConsumptionResponse> {
        val commodityAndServiceGroupDifference = houseHoldConsumptionCalculatorService.getConsumptionStats(
            commodityAndServiceGroupInput = request.commodityAndServiceGroup,
        )

        val responseBody = CompareConsumptionResponse(
            foodAndBeverages = commodityAndServiceGroupDifference.foodAndBeverages,
            alcoholTobaccoNarcotics = commodityAndServiceGroupDifference.alcoholTobaccoNarcotics,
            clothingAndFootwear = commodityAndServiceGroupDifference.clothingAndFootwear,
            housing = commodityAndServiceGroupDifference.housing,
            housingAndUtilities = commodityAndServiceGroupDifference.housingAndUtilities,
            furnishingsAndHouseholdEquipment = commodityAndServiceGroupDifference.furnishingsAndHouseholdEquipment,
            health = commodityAndServiceGroupDifference.health,
            transport = commodityAndServiceGroupDifference.transport,
            communication = commodityAndServiceGroupDifference.communication,
            recreationAndCulture = commodityAndServiceGroupDifference.recreationAndCulture,
            education = commodityAndServiceGroupDifference.education,
            restaurantsAndHotels = commodityAndServiceGroupDifference.restaurantsAndHotels,
            insuranceAndFinancialServices = commodityAndServiceGroupDifference.insuranceAndFinancialServices,
            personalCare = commodityAndServiceGroupDifference.personalCare
        )

        return ResponseEntity.ok(responseBody)
    }

}