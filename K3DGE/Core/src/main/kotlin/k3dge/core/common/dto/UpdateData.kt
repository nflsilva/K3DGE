package k3dge.core.common.dto

import k3dge.core.camera.Camera
import k3dge.core.common.BaseEntity
import k3dge.core.entity.Entity3D
import k3dge.core.light.Light
import k3dge.render.RenderEngine
import k3dge.ui.dto.InputStateData

data class UpdateData(
    val elapsedTime: Double,
    val input: InputStateData,
    val graphics: RenderEngine,
    val entity: BaseEntity? = null,
    val camera: Camera? = null,
    val light: Light? = null
)