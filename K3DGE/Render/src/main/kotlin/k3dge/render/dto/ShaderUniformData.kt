package k3dge.render.dto

import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class ShaderUniformData(val modelMatrix: Matrix4f,
                        viewMatrix: Matrix4f? = null,
                        projectionMatrix: Matrix4f? = null,
                        lightDirection: Vector3f? = null,
                        lightColor: Vector4f? = null,
                        lightSpaceMatrix: Matrix4f? = null,
                        ambientCoefficient: Float? = null,
                        shadowsEnabled: Boolean? = null) {

    val viewMatrix: Matrix4f
    val projectionMatrix: Matrix4f
    val lightDirection: Vector3f
    val lightColor: Vector4f
    val lightSpaceMatrix: Matrix4f
    val ambientCoefficient: Float
    val shadowsEnabled: Boolean

    init {
        this.viewMatrix = viewMatrix ?: Matrix4f().identity()
        this.projectionMatrix = projectionMatrix ?: Matrix4f().identity()
        this.lightDirection = lightDirection ?: Vector3f(1.0F)
        this.lightColor = lightColor ?: Vector4f(1.0F)
        this.lightSpaceMatrix = lightSpaceMatrix ?: Matrix4f().identity()
        this.ambientCoefficient = ambientCoefficient ?: 0.25f
        this.shadowsEnabled = shadowsEnabled?: true
    }
}