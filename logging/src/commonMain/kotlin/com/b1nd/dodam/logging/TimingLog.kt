package com.b1nd.dodam.logging

class TimingLog(private val label: String, tag: String? = null) {

    data class Timing(val time: Long, val msg: String, val isVerbose: Boolean)

    private val tagName = tag ?: KmLogging.createTag("TimingLog").first
    private val timing = ArrayList<Timing>()

    init {
        add("", false)
    }

    fun add(msg: String, isVerbose: Boolean) {
        timing.add(Timing(Platform.timeNanos, msg, isVerbose))
    }

    inline fun verbose(msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose) {
            add(msg().toString(), true)
        }
    }

    inline fun debug(msg: () -> Any?) {
        if (KmLogging.isLoggingDebug) {
            add(msg().toString(), false)
        }
    }

    /**
     * Report timing logs.
     * If minThresholdMs is greater than 0 then only report if total time exceeds this amount
     * @param minThresholdMs threshold in milliseconds
     */
    fun finish(minThresholdMs: Long = 0) {
        if (KmLogging.isLoggingDebug || timing.size > 1) {
            val first = timing[0].time
            var now = first
            var prevDebug = first
            var prev = first
            val totalTime = timing.last().time - first
            if (minThresholdMs > 0 && totalTime < minThresholdMs * 1_000_000) {
                return
            }
            KmLogging.debug(tagName, "$label: begin TimingLog")
            for (i in 1 until timing.size) {
                val t = timing[i]
                now = t.time
                val msg = t.msg
                val indent = if (t.isVerbose) "         " else "     "
                val timeStr = if (t.isVerbose) msDiff(now, prev) else msDiff(now, prevDebug)
                KmLogging.debug(tagName, "$label:$indent$timeStr $msg")
                prev = now
                if (!t.isVerbose)
                    prevDebug = now
            }
            KmLogging.debug(tagName, "$label: end ${msDiff(now, first)}")
        }
        reset()
    }

    fun reset() {
        timing.clear()
        add("", false)
    }

    private fun msDiff(nano1: Long, nano2: Long): String {
        val diff = (nano1 - nano2) / 1_000_000f
        return "${truncateDecimal(diff, 3)} ms"
    }

    companion object {
        fun truncateDecimal(num: Float, decPlaces: Int): String {
            val str = num.toString()
            val dotPos = str.indexOf('.')
            return if (dotPos >= 0 && dotPos < str.length - decPlaces)
                str.substring(0..(dotPos + decPlaces))
            else
                str
        }
    }
}