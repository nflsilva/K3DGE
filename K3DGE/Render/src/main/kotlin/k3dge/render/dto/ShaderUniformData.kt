package k3dge.render.dto

import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

data class ShaderUniformData(
    val modelMatrix: Matrix4f,
    val viewMatrix: Matrix4f? = null,
    val projectionMatrix: Matrix4f? = null,
    val lightDirection: Vector3f? = null,
    val lightColor: Vector4f? = null,
    val lightSpaceMatrix: Matrix4f? = null,
)