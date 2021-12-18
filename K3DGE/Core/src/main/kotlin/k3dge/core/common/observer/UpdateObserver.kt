package k3dge.core.common.observer

import k3dge.core.common.UpdateContext

interface UpdateObserver {
    fun onUpdate(context: UpdateContext)
}