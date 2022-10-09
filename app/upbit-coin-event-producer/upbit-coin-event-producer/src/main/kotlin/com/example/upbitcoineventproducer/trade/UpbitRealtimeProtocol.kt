package com.example.upbitcoineventproducer.trade

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

/**
 * https://docs.upbit.com/docs/upbit-quotation-websocket
 */
class UpbitRealtimeRequest(
    private val ticketField: UpbitRealtimeRequestTicketField = UpbitRealtimeRequestTicketField(),
    private val typeField: UpbitRealtimeRequestTypeField,
    private val formatField: UpbitRealTimeFormatField = UpbitRealTimeFormatField()
) {
    companion object {
        private val objectMapper = jacksonObjectMapper()

        fun of(type: String, codes: Set<String>) = UpbitRealtimeRequest(
            typeField = UpbitRealtimeRequestTypeField(type = type, codes = codes)
        )
    }

    data class UpbitRealtimeRequestTicketField(
        val ticket: String = UUID.randomUUID().toString()
    )

    data class UpbitRealtimeRequestTypeField(
        val type: String,
        val codes: Set<String>,
        val isOnlyRealtime: Boolean = true
    )

    data class UpbitRealTimeFormatField(
        val format: String = "SIMPLE"
    )

    /**
     * format: [{ticket field}, {type field}, {format field}]
     */
    fun build(): String {
        return listOf(
            objectMapper.writeValueAsString(ticketField),
            objectMapper.writeValueAsString(typeField),
            objectMapper.writeValueAsString(formatField)
        ).joinToString(prefix = "[", postfix = "]")
    }
}

data class UpbitRealTimeTradeResponse(
    // 타입
    @JsonProperty("ty")
    val type: String,

    // 마켓 코드 (ex. KRW-BTC)
    @JsonProperty("cd")
    val code: String,

    // 체결 가격
    @JsonProperty("tp")
    val tradePrice: Double,

    // 체결량
    @JsonProperty("tv")
    val tradeVolume: Double,

    // 매수/매도 구분
    @JsonProperty("ab")
    val askBid: String,

    // 전일 종가
    @JsonProperty("pcp")
    val prevClosingPrice: Double,

    // 전일 대비
    @JsonProperty("c")
    val change: String,

    // 부호 없는 전일 대비 값
    @JsonProperty("cp")
    val changePrice: Double,

    // 체결 일자(UTC 기준)
    @JsonProperty("td")
    val tradeDate: String,

    // 체결 시각(UTC 기준)
    @JsonProperty("ttm")
    val tradeTime: String,

    // 체결 타임스탬프 (millisecond)
    @JsonProperty("ttms")
    val tradeTimestamp: Long,

    // 타임스탬프 (millisecond)
    @JsonProperty("tms")
    val timestamp: Long,

    // 체결 번호 (Unique)
    @JsonProperty("sid")
    val sequentialId: Long,

    // 스트림 타입
    @JsonProperty("st")
    val streamType: String
) {
    fun toTrade() = Trade(
        type = this.type,
        code = this.code,
        tradePrice = Price(this.tradePrice),
        tradeVolume = this.tradeVolume,
        askBid = TradeType.valueOf(this.askBid),
        prevClosingPrice = Price(this.prevClosingPrice),
        change = TradeChange.valueOf(this.change),
        changePrice = Price(this.changePrice),
        tradeDate = LocalDate.parse(this.tradeDate, DateTimeFormatter.ISO_LOCAL_DATE),
        tradeTime = LocalTime.parse(this.tradeTime, DateTimeFormatter.ISO_LOCAL_TIME),
        tradeDateTime = LocalDateTime.ofInstant( Instant.ofEpochMilli(this.tradeTimestamp), ZoneId.of("Asia/Seoul")),
        localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.timestamp), ZoneId.of("Asia/Seoul")),
        sequentialId = this.sequentialId,
        streamType = this.streamType
    )
}
