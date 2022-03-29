package k3dge.render

import k3dge.render.common.dto.BatchEntityRenderData
import k3dge.render.common.dto.BatchRenderData
import k3dge.render.renderer3d.dto.EntityRenderData
import org.joml.Matrix4f
import org.joml.Vector3f

open class BaseRenderer {

    protected val renderBatches: MutableMap<String, BatchRenderData> = mutableMapOf()

    protected fun addEntityToRenderList(model: EntityRenderData) {
        val modelMatrix = computeModelMatrix(model.position, model.rotation, model.scale)
        val batchId = model.componentId.toString()
        val entityId = model.entityId.toString()

        if(!renderBatches.containsKey(batchId)){
            renderBatches[batchId] = BatchRenderData(model.mesh.vao, model.mesh.attributeArrays, model.mesh.size, model.texture.id)
        }
        renderBatches[batchId]?.let { batchData ->
            if(!batchData.entityData.containsKey(entityId)) {
                renderBatches[batchId]?.entityData?.set(entityId,
                    BatchEntityRenderData(model.entityId, model.shader, modelMatrix)
                )
            }
            else {
                renderBatches[batchId]?.entityData?.get(entityId)?.modelMatrix = modelMatrix
            }
        }

    }

    private fun computeModelMatrix(position: Vector3f, rotation: Vector3f, scale: Vector3f): Matrix4f {
        val modelMatrix = Matrix4f()
        modelMatrix.translation(position)
        modelMatrix.rotate(rotation.x, Vector3f(1.0f, 0.0f, 0.0f))
        modelMatrix.rotate(rotation.y, Vector3f(0.0f, 1.0f, 0.0f))
        modelMatrix.rotate(rotation.z, Vector3f(0.0f, 0.0f, 1.0f))
        modelMatrix.scale(scale)
        return modelMatrix
    }
}