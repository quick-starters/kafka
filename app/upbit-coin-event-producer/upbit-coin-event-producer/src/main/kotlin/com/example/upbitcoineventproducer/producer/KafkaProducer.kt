package com.example.upbitcoineventproducer.producer

import com.example.upbitcoineventproducer.logging.logger
import org.springframework.kafka.core.KafkaTemplate

class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val log = logger()

    companion object {
        // TODO
        const val TOPIC = "upbit-trade"
    }

    fun sendMessage(message: String) {
        kafkaTemplate.send(TOPIC, message)
    }
}
