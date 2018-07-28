# Neo [Sonar](https://fbsonar.com/)
`Sonar, open source by Facebook on [Github/Sonar], is a platfrom for debugging mobile apps on iOS and Android. Visualize, inspect, and control your apps from a simple desktop interface. Use Sonar as is or extend it using the plugin API.`
`This module is based on Sonar project, and provide flexable dagger needs injected module`.

# How to use
Sonar is a debuging tools, then its only used in Debug build type. It's has been added in 'neo' module debug  build type depedencies
```text
project/services/neo/src/debug/..../NeoModule.kt

@Module(
    includes = [
        ....
        SonarModule::class,
        SonarNetworkPluginModule::class,
        SonarInspectorPluginModule::class,
        SonarSharedPreferencePluginModule::class
        ....
    ]
)
 ```
