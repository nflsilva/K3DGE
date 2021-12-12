package k3dge.core

import k3dge.ui.dto.InputState

interface CoreEngineDelegate {
    fun onStart()
    fun onUpdate()
    fun onFrame(elapsedTime: Double, input: InputState)
    fun onCleanUp()
}