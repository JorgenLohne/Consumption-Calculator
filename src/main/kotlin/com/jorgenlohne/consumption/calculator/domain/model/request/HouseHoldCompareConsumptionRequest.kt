package com.jorgenlohne.consumption.calculator.domain.model.request

import com.jorgenlohne.consumption.calculator.domain.model.CommodityAndServiceGroup

data class HouseHoldCompareConsumptionRequest(
    val commodityAndServiceGroup: CommodityAndServiceGroup,
)
