package com.jorgenlohne.consumption.calculator.domain.service

import com.jorgenlohne.consumption.calculator.integration.ssb.service.SsbClient
import org.springframework.stereotype.Service

@Service
class ConsumptionCalculatorService(private val ssbClient: SsbClient) {

    fun getConsumptionStats(): String {

        //TODO: add (redis) cache?
        return ssbClient.getConsumptionStats()
    }
}