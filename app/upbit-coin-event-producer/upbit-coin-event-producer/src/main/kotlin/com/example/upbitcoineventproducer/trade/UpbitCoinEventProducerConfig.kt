package com.example.upbitcoineventproducer.trade

import com.example.upbitcoineventproducer.logging.logger
import com.example.upbitcoineventproducer.producer.KafkaProducer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UpbitCoinEventProducerConfig {
    private val log = logger()

    companion object {
        val MARKET = setOf(
            "KRW-BTC",
            "KRW-ETH",
            "KRW-NEO",
            "KRW-MTL",
            "KRW-XRP",
            "KRW-ETC",
            "KRW-OMG",
            "KRW-SNT",
            "KRW-WAVES",
            "KRW-XEM",
            "KRW-QTUM",
            "KRW-LSK",
            "KRW-STEEM",
            "KRW-XLM",
            "KRW-ARDR",
            "KRW-ARK",
            "KRW-STORJ",
            "KRW-GRS",
            "KRW-REP",
            "KRW-ADA",
            "KRW-SBD",
            "KRW-POWR",
            "KRW-BTG",
            "KRW-ICX",
            "KRW-EOS",
            "KRW-TRX",
            "KRW-SC",
            "KRW-ONT",
            "KRW-ZIL",
            "KRW-POLY",
            "KRW-ZRX",
            "KRW-LOOM",
            "KRW-BCH",
            "KRW-BAT",
            "KRW-IOST",
            "KRW-RFR",
            "KRW-CVC",
            "KRW-IQ",
            "KRW-IOTA",
            "KRW-MFT",
            "KRW-ONG",
            "KRW-GAS",
            "KRW-UPP",
            "KRW-ELF",
            "KRW-KNC",
            "KRW-BSV",
            "KRW-THETA",
            "KRW-QKC",
            "KRW-BTT",
            "KRW-MOC",
            "KRW-ENJ",
            "KRW-TFUEL",
            "KRW-MANA",
            "KRW-ANKR",
            "KRW-AERGO",
            "KRW-ATOM",
            "KRW-TT",
            "KRW-CRE",
            "KRW-MBL",
            "KRW-WAXP",
            "KRW-HBAR",
            "KRW-MED",
            "KRW-MLK",
            "KRW-STPT",
            "KRW-ORBS",
            "KRW-VET",
            "KRW-CHZ",
            "KRW-STMX",
            "KRW-DKA",
            "KRW-HIVE",
            "KRW-KAVA",
            "KRW-AHT",
            "KRW-LINK",
            "KRW-XTZ",
            "KRW-BORA",
            "KRW-JST",
            "KRW-CRO",
            "KRW-TON",
            "KRW-SXP",
            "KRW-HUNT",
            "KRW-PLA",
            "KRW-DOT",
            "KRW-SRM",
            "KRW-MVL",
            "KRW-STRAX",
            "KRW-AQT",
            "KRW-GLM",
            "KRW-SSX",
            "KRW-META",
            "KRW-FCT2",
            "KRW-CBK",
            "KRW-SAND",
            "KRW-HUM",
            "KRW-DOGE",
            "KRW-STRK",
            "KRW-PUNDIX",
            "KRW-FLOW",
            "KRW-DAWN",
            "KRW-AXS",
            "KRW-STX",
            "KRW-XEC",
            "KRW-SOL",
            "KRW-MATIC",
            "KRW-AAVE",
            "KRW-1INCH",
            "KRW-ALGO",
            "KRW-NEAR",
            "KRW-WEMIX",
            "KRW-AVAX",
            "KRW-T",
            "KRW-CELO",
            "KRW-GMT"
        )
    }

    @Bean
    fun upbitRealtimeTradeService(kafkaProducer: KafkaProducer): UpbitRealtimeTradeService {
        val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        return UpbitRealtimeTradeService(MARKET, {
            kafkaProducer.sendMessage(objectMapper.writeValueAsString(it))
        })
    }
}
