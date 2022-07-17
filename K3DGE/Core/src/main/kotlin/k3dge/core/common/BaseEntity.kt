package k3dge.core.common

import k3dge.core.common.dto.UpdateData
import k3dge.core.common.observer.CleanupObserver
import k3dge.core.common.observer.SignalObserver
import k3dge.core.common.observer.UpdateObserver
import k3dge.core.entity.component.TransformEntityComponent
import k3dge.tools.Util
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector4f
import java.util.*

abstract class BaseEntity(var transform: TransformEntityComponent) {

    val uid: UUID = UUID.randomUUID()
    private val updateObservers: MutableList<UpdateObserver> = mutableListOf()
    private val signalObservers: MutableList<SignalObserver> = mutableListOf()
    private val cleanupObservers: MutableList<CleanupObserver> = mutableListOf()

    fun onUpdate(context: UpdateData) {
        for(c in updateObservers) {
            c.onUpdate(context)
        }
    }
    fun onSignal(signal: ComponentSignal){
        for(s in signalObservers){
            s.onSignal(signal)
        }
    }
    fun cleanUp() {
        for(c in cleanupObservers) {
            c.onCleanup()
        }
    }

    fun rotateAroundPoint(delta: Float, axis: Vector3f, point: Vector3f){
        val angle = Util.degreeToRadian(delta)
        val q = Quaternionf().rotateAxis(angle, axis)
        val rotation = Matrix4f().rotateAround(q, point.x, point.y, point.z)
        val newPosition = Vector4f(transform.position.x, transform.position.y, transform.position.z, 1.0F).mul(rotation)

        transform.position.x = newPosition.x
        transform.position.y = newPosition.y
        transform.position.z = newPosition.z
    }
    fun addComponent(component: Component){
        component.updateObserver?.let { o -> updateObservers.add(o) }
        component.signalObserver?.let { o -> signalObservers.add(o) }
        component.cleanupObserver?.let { o -> cleanupObservers.add(o) }
    }
}