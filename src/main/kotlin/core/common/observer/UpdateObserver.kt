package core.common.observer

import core.common.dto.UpdateData

interface UpdateObserver {
    fun onUpdate(context: UpdateData)
}