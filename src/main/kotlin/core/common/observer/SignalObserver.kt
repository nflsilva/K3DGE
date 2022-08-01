package core.common.observer

import core.common.ComponentSignal

interface SignalObserver {
    fun onSignal(signal: ComponentSignal)
}