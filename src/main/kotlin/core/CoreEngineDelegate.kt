package core

import ui.dto.InputStateData

interface CoreEngineDelegate {
    fun onStart()
    fun onUpdate(elapsedTime: Double, input: InputStateData)
    fun onFrame()
    fun onCleanUp()
}