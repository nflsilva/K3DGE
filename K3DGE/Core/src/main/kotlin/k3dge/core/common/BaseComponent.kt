package k3dge.core.common

import k3dge.render.RenderEngine
import k3dge.ui.dto.InputStateData
import java.util.*

abstract class BaseComponent {

    protected val uid: String = UUID.randomUUID().toString()

    abstract fun cleanUp()
    abstract fun onUpdate(entity: BaseEntity, elapsedTime: Double, input: InputStateData)
    abstract fun onSignal(signal: ComponentSignal)
}