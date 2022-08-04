package core.common.dto

import core.camera.Camera
import core.common.BaseEntity
import core.light.Light
import physics.PhysicsEngine
import render.RenderEngine
import ui.dto.InputStateData

data class UpdateData(
    val elapsedTime: Double,
    val input: InputStateData,
    val graphics: RenderEngine,
    val physics: PhysicsEngine,
    val entity: BaseEntity? = null,
    val camera: Camera? = null,
    val light: Light? = null
)