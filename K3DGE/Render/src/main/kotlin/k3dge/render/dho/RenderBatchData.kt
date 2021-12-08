package k3dge.render.dho

data class RenderBatchData(
    val vao: Int,
    val meshSize: Int,
    val textureId: Int,
    val entityData: MutableList<EntityRenderData> = mutableListOf()
)
