package k3dge.render.common.dto

data class ColorData(val r: Float, val g: Float, val b: Float, val a: Float){
    constructor(all: Float): this(all, all, all ,all)
}
