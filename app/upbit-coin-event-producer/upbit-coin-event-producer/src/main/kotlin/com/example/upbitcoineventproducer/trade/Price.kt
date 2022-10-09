package com.example.upbitcoineventproducer.trade

import kotlin.math.abs

data class Price(
    val value: Double
) {
    constructor(value: Long) : this(value.toDouble())

    init {
        if (value < 0) {
            throw IllegalArgumentException("Price must always be greater than 0.")
        }
    }

    companion object {
        val ZERO = Price(0)
        val MILLION_WON = Price(100_000_000)
    }

    operator fun plus(other: Price): Price {
        return Price(this.value + other.value)
    }

    operator fun times(value: Int): Price {
        return Price(this.value.times(value))
    }

    operator fun times(value: Double): Price {
        return Price(this.value.times(value))
    }

    operator fun div(value: Int): Price {
        return Price(this.value / value)
    }

    operator fun div(other: Price): Price {
        return Price(this.value / other.value)
    }

    operator fun compareTo(other: Price): Int {
        return value.compareTo(other.value)
    }

    fun diff(other: Price): Price {
        return Price(abs(this.value - other.value))
    }

    fun diffPercent(other: Price): Double {
        return (abs(this.value - other.value) / this.value) * 100
    }
}
