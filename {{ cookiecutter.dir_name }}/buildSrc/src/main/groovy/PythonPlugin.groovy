import org.gradle.api.Plugin
import org.gradle.api.Project


class PythonPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("python", PythonPluginExtension)

        project.task('buildPythonDebug') {
            doLast {
                project.exec {
                    commandLine "voc -v -p app -o build/intermediates/classes/debug app".split()
                }
                project.exec {
                    commandLine "voc -v -p app_packages -o build/intermediates/classes/debug app_packages".split()
                }
            }
        }

        project.task('buildPythonRelease') {
            doLast {
                project.exec {
                    commandLine "voc -v -p app -o build/intermediates/classes/release app".split()
                }
                project.exec {
                    commandLine "voc -v -p app_packages -o build/intermediates/classes/release app_packages".split()
                }
            }
        }

        project.task('run') {
            doLast {
                project.exec {
                    commandLine "adb shell am start -n ${project.voc.namespace}/android.PythonActivity".split()
                }
            }
        }
    }
}


class PythonPluginExtension {
    String namespace = "com.example"
}
