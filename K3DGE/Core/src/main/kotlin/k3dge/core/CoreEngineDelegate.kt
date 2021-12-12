package k3dge.core

import k3dge.ui.dto.InputStateData

interface CoreEngineDelegate {
    fun onStart()
    fun onUpdate()
    fun onFrame(elapsedTime: Double, input: InputStateData)
    fun onCleanUp()
}