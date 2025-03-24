import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.olimzhanovumid"
version = "0.0.1"

kotlin {
    jvm()

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(
        groupId = group.toString(),
        artifactId = "library",
        version = version.toString()
    )

    pom {
        name = "Quelana"
        description = "A jvm library for generating BufferedImage from text and images."
        inceptionYear = "2025"
        url = "https://github.com/OlimzhanovUmid/Quelana/"
        licenses {
            license {
                name = "GNU General Public License"
                url = "https://www.gnu.org/licenses/gpl-3.0.html"
                distribution = "https://www.gnu.org/licenses/gpl-3.0.txt"
            }
        }
        developers {
            developer {
                id = "UOlimzh"
                name = "Umid Olimzhanov"
                url = "https://github.com/OlimzhanovUmid/"
            }
        }
        scm {
            url = "https://github.com/OlimzhanovUmid/Quelana"
            connection = "scm:git:git://github.com/OlimzhanovUmid/Quelana.git"
            developerConnection = "scm:git:ssh://git@github.com/OlimzhanovUmid/Quelana.git"
        }
    }
}