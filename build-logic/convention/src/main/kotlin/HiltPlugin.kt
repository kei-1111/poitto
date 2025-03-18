import com.example.flush.implementation
import com.example.flush.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

            dependencies {
                "ksp"(libs.findLibrary("hilt.android.compiler").get())
                implementation(libs.findLibrary("hilt.android").get())
            }
        }
    }
}