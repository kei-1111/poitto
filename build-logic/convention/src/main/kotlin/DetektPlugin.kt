import com.example.flush.detektPlugins
import com.example.flush.libs
import com.example.flush.setupDetekt
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "io.gitlab.arturbosch.detekt")

            setupDetekt(extensions.getByType<DetektExtension>())

            dependencies {
                detektPlugins(libs.findLibrary("detekt.compose").get())
                detektPlugins(libs.findLibrary("detekt.formatting").get())
            }

            tasks.withType<Detekt>().configureEach {
                jvmTarget = JvmTarget.JVM_17.toString()
            }
        }
    }
}