package com.jorgenlohne.consumption.calculator.domain.service

import com.jorgenlohne.consumption.calculator.domain.model.CommodityAndServiceGroup
import com.jorgenlohne.consumption.calculator.integration.ssb.service.SsbClient
import org.springframework.stereotype.Service

@Service
class HouseHoldConsumptionCalculatorService(private val ssbClient: SsbClient) {

    fun getConsumptionStats(
        commodityAndServiceGroupInput: CommodityAndServiceGroup
    ): CommodityAndServiceGroup {

        val ssbResponse = ssbClient.getConsumptionStats()

        val commodityAndServiceGroupHouseHoldAverage = CommodityAndServiceGroup(
            foodAndBeverages = ssbResponse.value[1],
            alcoholTobaccoNarcotics = ssbResponse.value[2],
            clothingAndFootwear = ssbResponse.value[3],
            housing = ssbResponse.value[4],
            furnishingsAndHouseholdEquipment = ssbResponse.value[5],
            health = ssbResponse.value[6],
            transport = ssbResponse.value[7],
            communication = ssbResponse.value[8],
            recreationAndCulture = ssbResponse.value[9],
            education = ssbResponse.value[10],
            restaurantsAndHotels = ssbResponse.value[11],
            insuranceAndFinancialServices = ssbResponse.value[12],
            other = ssbResponse.value[13],
        )

        val commodityAndServiceGroupDifference = CommodityAndServiceGroup(
            foodAndBeverages = commodityAndServiceGroupInput.foodAndBeverages?.minus(commodityAndServiceGroupHouseHoldAverage.foodAndBeverages ?: 0),
            alcoholTobaccoNarcotics = commodityAndServiceGroupInput.alcoholTobaccoNarcotics?.minus(commodityAndServiceGroupHouseHoldAverage.alcoholTobaccoNarcotics ?: 0),
            clothingAndFootwear = commodityAndServiceGroupInput.clothingAndFootwear?.minus(commodityAndServiceGroupHouseHoldAverage.clothingAndFootwear ?: 0),
            housing = commodityAndServiceGroupInput.housing?.minus(commodityAndServiceGroupHouseHoldAverage.housing ?: 0),
            furnishingsAndHouseholdEquipment = commodityAndServiceGroupInput.furnishingsAndHouseholdEquipment?.minus(commodityAndServiceGroupHouseHoldAverage.furnishingsAndHouseholdEquipment ?: 0),
            health = commodityAndServiceGroupInput.health?.minus(commodityAndServiceGroupHouseHoldAverage.health ?: 0),
            transport = commodityAndServiceGroupInput.transport?.minus(commodityAndServiceGroupHouseHoldAverage.transport ?: 0),
            communication = commodityAndServiceGroupInput.communication?.minus(commodityAndServiceGroupHouseHoldAverage.communication ?: 0),
            recreationAndCulture = commodityAndServiceGroupInput.recreationAndCulture?.minus(commodityAndServiceGroupHouseHoldAverage.recreationAndCulture ?: 0),
            education = commodityAndServiceGroupInput.education?.minus(commodityAndServiceGroupHouseHoldAverage.education ?: 0),
            restaurantsAndHotels = commodityAndServiceGroupInput.restaurantsAndHotels?.minus(commodityAndServiceGroupHouseHoldAverage.restaurantsAndHotels ?: 0),
            insuranceAndFinancialServices = commodityAndServiceGroupInput.insuranceAndFinancialServices?.minus(commodityAndServiceGroupHouseHoldAverage.insuranceAndFinancialServices ?: 0),
            other = commodityAndServiceGroupInput.other?.minus(commodityAndServiceGroupHouseHoldAverage.other ?: 0),
        )

        return commodityAndServiceGroupDifference
    }

}