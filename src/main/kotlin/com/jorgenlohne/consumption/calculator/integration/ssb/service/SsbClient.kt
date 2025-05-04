package com.jorgenlohne.consumption.calculator.integration.ssb.service

import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Query
import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Selection
import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.SsbConsumptionRequest
import com.jorgenlohne.consumption.calculator.integration.ssb.model.response.SsbHouseHoldConsumptionResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val BASE_URL = "https://data.ssb.no/api"

@Service
class SsbClient {

    private val logger = LoggerFactory.getLogger(SsbClient::class.java)
    private val client = OkHttpClient()

    /**
     * **See Also:** [SSB table 14100](https://www.ssb.no/statbank/table/14100/)
     * */
    fun getConsumptionStats(): SsbHouseHoldConsumptionResponse {

        val request = buildConsumptionStatsRequest()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) {
                logger.error("Unexpected response code: ${response.code}")
                throw IllegalStateException("Unexpected response: ${response.code}")
            }

            val jsonResponseBody = response.body?.string()

            if (jsonResponseBody == null) {
                logger.error("Response body is null")
                throw IllegalStateException("Response body is null")
            }

            val jsonDecoder = Json { ignoreUnknownKeys = true } //we only care about the fields we need in the response
            val response = jsonDecoder.decodeFromString<SsbHouseHoldConsumptionResponse>(jsonResponseBody)

            return response
        }
    }

    private fun buildConsumptionStatsRequest(): Request {
        val requestBody = buildRequestBody()
        val mediaType = "application/json".toMediaType()
        val jsonBody = Json.encodeToString(requestBody).toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$BASE_URL/v0/no/table/14100/")
            .post(jsonBody)
            .build()

        return request
    }

    private fun buildRequestBody(): SsbConsumptionRequest = SsbConsumptionRequest(
        query = listOf(
            Query(
                code = "VareTjenesteGruppe",
                selection = Selection(
                    filter = "agg_single:CoiCop2018a1",
                    values = listOf(
                        "00",
                        "01",
                        "02",
                        "03",
                        "04",
                        "05",
                        "06",
                        "07",
                        "08",
                        "09",
                        "10",
                        "11",
                        "12",
                        "13"
                    )
                )
            ),
            Query(
                code = "ContentsCode",
                selection = Selection(
                    filter = "item",
                    values = listOf("Utgift")
                )
            )
        ),
        response = com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Response(
            format = "json-stat2"
        )
    )

}
