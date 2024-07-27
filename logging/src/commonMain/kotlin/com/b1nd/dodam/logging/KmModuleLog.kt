package com.b1nd.dodam.logging

class KmModuleLog(val log: KmLog, val isModuleLogging: () -> Boolean) {

    inline fun v(msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(msg)
    }

    inline fun v(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(tag, msg)
    }

    inline fun d(msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(msg)
    }

    inline fun d(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(tag, msg)
    }

    inline fun i(msg: () -> Any?) {
        if (isModuleLogging())
            log.info(msg)
    }

    inline fun i(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.info(tag, msg)
    }

    inline fun w(msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(msg)
    }

    inline fun w(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(err, tag, msg)
    }

    inline fun e(msg: () -> Any?) {
        if (isModuleLogging())
            log.error(msg)
    }

    inline fun e(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.error(err, tag, msg)
    }
}