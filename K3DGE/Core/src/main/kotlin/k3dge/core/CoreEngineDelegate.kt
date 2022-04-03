package k3dge.core

import k3dge.ui.dto.InputStateData

interface CoreEngineDelegate {
    fun onStart()
    fun onUpdate(elapsedTime: Double, input: InputStateData)
    fun onFrame()
    fun onCleanUp()
}