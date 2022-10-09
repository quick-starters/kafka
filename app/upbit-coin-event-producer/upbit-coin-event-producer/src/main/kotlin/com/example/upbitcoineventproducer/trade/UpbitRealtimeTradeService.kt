package com.example.upbitcoineventproducer.trade

import com.example.upbitcoineventproducer.logging.logger
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import java.net.URI
import java.util.function.Consumer

class UpbitRealtimeTradeService(
    private val markets: Set<String>,
    private val eventConsumer: Consumer<Trade>
) {
    private val log = logger()

    companion object {
        const val URL = "wss://api.upbit.com/websocket/v1"
        val objectMapper = jacksonObjectMapper().apply {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }
    }

    init {
        val session = StandardWebSocketClient().doHandshake(object : BinaryWebSocketHandler() {
            override fun afterConnectionEstablished(session: WebSocketSession) {
                log.info("Upbit Realtime Trade socket connection established. [session: $session]")
            }

            override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
                log.warn("Upbit Realtime Trade socket connection closed. [session: $session, status: $status]")
            }

            override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
                val response = objectMapper.readValue(message.payload.array(), UpbitRealTimeTradeResponse::class.java)
                eventConsumer.accept(response.toTrade())
            }
        }, WebSocketHttpHeaders(), URI(URL)).get()

        val request = UpbitRealtimeRequest.of("trade", markets)
        session.sendMessage(TextMessage(request.build()))
    }
}
