package com.example.upbitcoineventproducer.trade

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Trade(
    val type: String,
    val code: String,
    val tradePrice: Price,
    val tradeVolume: Double,
    val askBid: TradeType,
    val prevClosingPrice: Price,
    val change: TradeChange,
    val changePrice: Price,
    val tradeDate: LocalDate,
    val tradeTime: LocalTime,
    val tradeDateTime: LocalDateTime,
    val localDateTime: LocalDateTime,
    val sequentialId: Long,
    val streamType: String
)

enum class TradeType(
    val description: String
) {
    BID("매수"),
    ASK("매도")
}

enum class TradeChange(
    val description: String
) {
    RISE("상승"),
    EVEN("보합"),
    FALL("하락")
}
