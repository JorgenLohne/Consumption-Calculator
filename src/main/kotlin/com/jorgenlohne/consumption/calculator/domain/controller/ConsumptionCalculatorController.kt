package com.jorgenlohne.consumption.calculator.domain.controller

import com.jorgenlohne.consumption.calculator.domain.service.ConsumptionCalculatorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/"])
class ConsumptionCalculatorController(private val consumptionCalculatorService: ConsumptionCalculatorService) {

    @GetMapping(path = ["consumption"])
    fun getConsumption(): ResponseEntity<String> {
        val responseBody = consumptionCalculatorService.getConsumptionStats()

        return ResponseEntity.ok(responseBody)
    }

}