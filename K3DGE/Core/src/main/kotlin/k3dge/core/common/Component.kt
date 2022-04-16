package k3dge.core.common

import k3dge.core.common.dto.UpdateData
import k3dge.core.common.observer.CleanupObserver
import k3dge.core.common.observer.SignalObserver
import k3dge.core.common.observer.UpdateObserver
import java.util.*

abstract class Component {
    protected val uid: UUID = UUID.randomUUID()
    var updateObserver: UpdateObserver? = null
    var signalObserver: SignalObserver? = null
    var cleanupObserver: CleanupObserver? = null

    protected fun setUpdateObserver(observerMethod: (context: UpdateData) -> Unit) {
        updateObserver = object: UpdateObserver {
            override fun onUpdate(context: UpdateData) {
                observerMethod(context)
            }
        }
    }
    protected fun setSignalObserver(observerMethod: (signal: ComponentSignal) -> Unit) {
        signalObserver = object: SignalObserver {
            override fun onSignal(signal: ComponentSignal) {
                observerMethod(signal)
            }
        }
    }
    protected fun setCleanupObserver(observerMethod: () -> Unit) {
        cleanupObserver = object: CleanupObserver {
            override fun onCleanup() {
                observerMethod()
            }

        }
    }
}