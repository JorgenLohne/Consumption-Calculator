package com.jorgenlohne.consumption.calculator.integration.ssb.service

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SsbClient {

    private val logger = LoggerFactory.getLogger(SsbClient::class.java)
    private val baseUrl = "https://data.ssb.no/api/v0/no/table/14152/"
    private val client = OkHttpClient()

    fun getConsumptionStats(): String {
        val requestBody = """
            {
              "query": [
                {
                  "code": "VareTjenesteGruppe",
                  "selection": {
                    "filter": "agg_single:CoiCop2018b1",
                    "values": [
                      "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"
                    ]
                  }
                },
                {
                  "code": "HusholdType",
                  "selection": {
                    "filter": "item",
                    "values": [
                      "0", "1", "2"
                    ]
                  }
                }
              ],
              "response": {
                "format": "json-stat2"
              }
            }
        """.trimIndent()

        val mediaType = "application/json".toMediaType()
        val body = requestBody.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(baseUrl)
            .post(body)
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) {
                logger.error("Unexpected response code: ${response.code}")
                throw IllegalStateException("Unexpected response: ${response.code}")
            }

            logger.info("Retrieved data from SSB")
            return response.body?.string() ?: throw IllegalStateException("Response body is null")
        }
    }
}
