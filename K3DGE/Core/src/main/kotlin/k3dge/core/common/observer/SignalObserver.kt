package k3dge.core.common.observer

import k3dge.core.common.ComponentSignal

interface SignalObserver {
    fun onSignal(signal: ComponentSignal)
}