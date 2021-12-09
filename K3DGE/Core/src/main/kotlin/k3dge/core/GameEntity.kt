package k3dge.core

import k3dge.core.component.Component
import k3dge.render.RenderEngine
import k3dge.render.model.ShaderModel
import k3dge.ui.InputState
import org.joml.Vector3f

open class GameEntity(val position: Vector3f,
                      val rotation: Vector3f,
                      val scale: Vector3f,
                      val shader: ShaderModel) {

    private val components: MutableList<Component> = mutableListOf()

    fun addComponent(component: Component){
        components.add(component)
    }
    open fun onUpdate(elapsedTime: Double, input: InputState) {
        for(c in components) {
            c.onUpdate(elapsedTime, input)
        }
    }
    open fun onFrame(graphics: RenderEngine) {
        for(c in components) {
            c.onFrame(this, graphics)
        }
    }
    open fun cleanUp() {
        for(c in components) {
            c.cleanUp()
        }
    }
}