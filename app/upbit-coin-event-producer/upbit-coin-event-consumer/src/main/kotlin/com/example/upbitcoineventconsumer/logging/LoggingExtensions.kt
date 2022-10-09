package com.example.upbitcoineventproducer.logging

import mu.KotlinLogging

inline fun <reified T> T.logger() = KotlinLogging.logger { }
