import com.example.flush.implementation
import com.example.flush.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "flush.android.library.compose")
            apply(plugin = "flush.hilt")

            dependencies {
                implementation(project(":core:base"))
                implementation(project(":core:designsystem"))

                implementation(libs.findLibrary("androidx.activity.compose").get())
                implementation(libs.findLibrary("hilt.navigation.compose").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.material3").get())
            }
        }
    }
}