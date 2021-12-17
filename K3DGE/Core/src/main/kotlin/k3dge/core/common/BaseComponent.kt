package k3dge.core.common

import k3dge.render.RenderEngine
import k3dge.ui.dto.InputStateData
import java.util.*

abstract class BaseComponent {

    protected val uid: UUID = UUID.randomUUID()

    abstract fun cleanUp()
    abstract fun onUpdate(entity: BaseEntity, elapsedTime: Double, input: InputStateData)
    abstract fun onSignal(signal: ComponentSignal)
}