package com.example.upbitcoineventconsumer.consumer

import com.example.upbitcoineventproducer.logging.logger
import com.example.upbitcoineventproducer.trade.Price
import com.example.upbitcoineventproducer.trade.Trade
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    private val log = logger()
    companion object {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    }

    @KafkaListener(topics = ["upbit-trade"], groupId = "evan")
    fun consume(message: String?) {
        val trade = objectMapper.readValue(message, Trade::class.java)
//        log.info("Consumed message : {}", trade)
        val price = trade.tradePrice * trade.tradeVolume
        if (price > Price(10000000)) {
            log.info("고래 출몰: {}", trade)
        }
    }
}