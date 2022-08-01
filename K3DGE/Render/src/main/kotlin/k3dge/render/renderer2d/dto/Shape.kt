package k3dge.render.renderer2d.dto

import k3dge.render.common.dto.ColorData

data class Shape(val type: Int,
                 val color: ColorData) {

    enum class Type(val value: Int) {
        SQUARE(0),
        CIRCLE(1),
        DONUT(2),
    }

}