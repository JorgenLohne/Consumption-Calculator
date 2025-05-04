package com.jorgenlohne.consumption.calculator.integration.ssb.service

import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Query
import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Selection
import com.jorgenlohne.consumption.calculator.integration.ssb.model.request.SsbConsumptionRequest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

private const val BASE_URL = "https://data.ssb.no/api/v0/no/table/14152/"

@Service
class SsbClient {

    private val logger = LoggerFactory.getLogger(SsbClient::class.java)
    private val client = OkHttpClient()

    /**
     * **See Also:** [SSB table 14152](https://www.ssb.no/statbank/table/14152/)
     * */
    fun getConsumptionStats(): String {

        val request = buildConsumptionStatsRequest()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) {
                logger.error("Unexpected response code: ${response.code}")
                throw IllegalStateException("Unexpected response: ${response.code}")
            }

            logger.info("Retrieved data from SSB")
            return response.body?.string() ?: throw IllegalStateException("Response body is null")
        }
    }

    private fun buildConsumptionStatsRequest(): Request {
        val queries = listOf(
            Query(
                code = "VareTjenesteGruppe",
                selection = Selection(
                    filter = "agg_single:CoiCop2018b1",
                    values = listOf(
                        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
                    )
                )
            ),
            Query(
                code = "HusholdType",
                selection = Selection(
                    filter = "item",
                    values = listOf(
                        "0", "1", "2"
                    )
                )
            )
        )

        val requestBody = SsbConsumptionRequest(
            query = queries,
            response = com.jorgenlohne.consumption.calculator.integration.ssb.model.request.Response(
                format = "json-stat2"
            )
        )

        val mediaType = "application/json".toMediaType()
        val jsonBody = Json.encodeToString(requestBody).toRequestBody(mediaType)

        val request = Request.Builder()
            .url(BASE_URL)
            .post(jsonBody)
            .build()

        return request
    }

}
