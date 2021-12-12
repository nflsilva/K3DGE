package k3dge.render.dto

data class RenderBatchData(
    val vao: Int,
    val meshSize: Int,
    val textureId: Int,
    val entityData: MutableList<EntityRenderData> = mutableListOf()
)
