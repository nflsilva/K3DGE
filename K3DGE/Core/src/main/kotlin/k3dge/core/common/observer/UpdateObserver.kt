package k3dge.core.common.observer

import k3dge.core.common.dto.UpdateData

interface UpdateObserver {
    fun onUpdate(context: UpdateData)
}