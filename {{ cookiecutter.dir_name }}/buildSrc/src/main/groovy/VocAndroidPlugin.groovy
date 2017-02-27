import org.gradle.api.Plugin
import org.gradle.api.Project


class VocPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("voc", VocPluginExtension)

        project.task('vocBuild') {
            doLast {
                def jarPath = "${project.voc.buildFromSourceDir}/dist/android/libs/python-android-support.jar"
                if (project.voc.buildFromSourceDir) {
                    project.exec {
                        workingDir "${project.voc.buildFromSourceDir}"
                        commandLine 'ant android'.split()
                    }
                }
                project.copy {
                    from "${jarPath}"
                    into 'libs/'
                }
                project.exec { commandLine "voc -v -p app -o bin/classes app".split() }
                project.exec { commandLine "voc -v -p app_packages -o bin/classes app_packages".split() }
                project.exec {
                    workingDir 'bin/classes'
                    commandLine "jar cvf python-app.jar .".split()
                }
                project.copy {
                    from "bin/classes/python-app.jar"
                    into 'libs/'
                }
            }
        }

        project.task('runAndroidApp') {
            doLast {
                project.exec {
                    commandLine "adb shell am start -n ${project.voc.namespace}/android.PythonActivity".split()
                }
            }
        }
    }
}


class VocPluginExtension {
    String namespace = "com.example"
    def buildFromSourceDir
}
