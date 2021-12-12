package k3dge.tools

import kotlin.math.max
import kotlin.math.min

class Util {
    companion object {
        fun degreeToRadian(degree: Float): Float {
            val pi = 3.14159265359
            return (degree * (pi / 180)).toFloat()
        }
        fun Float.clamp(min: Float, max: Float): Float{
            return min(max(max, -0.75f), min)
        }
    }
}