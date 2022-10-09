package com.example.upbitcoineventproducer.producer

import org.springframework.kafka.core.KafkaTemplate

class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    companion object {
        // TODO
        const val TOPIC = "TBD"
    }

    fun sendMessage(message: String) {
        kafkaTemplate.send(TOPIC, message)
    }
}
