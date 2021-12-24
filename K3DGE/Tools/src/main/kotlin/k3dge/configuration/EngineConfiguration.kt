package k3dge.configuration

class EngineConfiguration(var resolutionWidth: Int,
                          var resolutionHeight: Int,
                          var enableShadows: Boolean,
                          var shadowResolutionWidth: Int,
                          var shadowResolutionHeight: Int,
                          val renderDistance: Int,
                          val enableVsync: Boolean) {

    companion object {
        fun default(): EngineConfiguration{
            return EngineConfiguration(
                1920,
                1080,
                true,
                1920 * 2,
                1080 * 2,
                100,
                true)
        }
    }
}