package k3dge.core.common

import k3dge.render.RenderEngine
import k3dge.tools.Util
import k3dge.ui.dto.InputStateData
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f
import java.util.*

abstract class BaseEntity(var position: Vector3f) {

    val uid: UUID = UUID.randomUUID()
    protected val components: MutableList<BaseComponent> = mutableListOf()
    private val signals: MutableList<ComponentSignal> = mutableListOf()

    fun addComponent(component: BaseComponent){
        components.add(component)
    }
    fun onUpdate(elapsedTime: Double, input: InputStateData) {
        for(c in components) {
            for(s in signals){
                c.onSignal(s)
            }
            c.onUpdate(this, elapsedTime, input)
        }
        signals.clear()
    }
    fun onSignal(message: ComponentSignal){
        signals.add(message)
    }
    fun cleanUp() {
        for(c in components) {
            c.cleanUp()
        }
    }
    abstract fun onFrame(graphics: RenderEngine)

    fun rotateAroundPoint(delta: Float, axis: Vector3f, point: Vector3f){
        val angle = Util.degreeToRadian(delta)
        val q = Quaternionf().rotateAxis(angle, axis)
        val rotation = Matrix4f().rotateAround(q, point.x, point.y, point.z)
        val newPosition = Vector4f(position.x, position.y, position.z, 1.0F).mul(rotation)

        position.x = newPosition.x
        position.y = newPosition.y
        position.z = newPosition.z
    }
}