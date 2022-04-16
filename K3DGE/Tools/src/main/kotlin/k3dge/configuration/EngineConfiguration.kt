package k3dge.configuration

class EngineConfiguration(var windowTitle: String,
                          var resolutionWidth: Int,
                          var resolutionHeight: Int,
                          var enableShadows: Boolean,
                          var shadowResolutionWidth: Int,
                          var shadowResolutionHeight: Int,
                          var renderDistance: Int,
                          var enableVsync: Boolean) {

    companion object {
        fun default(): EngineConfiguration{
            return EngineConfiguration(
                "K3DGE",
                1280,
                720,
                true,
                1920 * 2,
                1080 * 2,
                1000,
                true)
        }
    }
}