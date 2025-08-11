plugins {
	java
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
	id("checkstyle")
}

group = "com.stream_app"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(24)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.postgresql:postgresql")
	
	// Lombok for reducing boilerplate code
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("com.navercorp.fixturemonkey:fixture-monkey-jackson:1.0.12")
	testImplementation("com.h2database:h2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	 testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Mockito + Mockito Kotlin
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")

    // AssertJ
    testImplementation("org.assertj:assertj-core:3.25.1")

    // Fixture Monkey
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.1.9")

}

// Configure source sets for integration tests
sourceSets {
    create("integration") {
        java {
            srcDir("src/integration/java")
        }
        resources {
            srcDir("src/integration/resources")
        }
        compileClasspath += sourceSets["main"].output + sourceSets["test"].output
        runtimeClasspath += sourceSets["main"].output + sourceSets["test"].output
    }
}

// Configure integration test dependencies
configurations {
    getByName("integrationImplementation").extendsFrom(configurations["testImplementation"])
    getByName("integrationRuntimeOnly").extendsFrom(configurations["testRuntimeOnly"])
}

// Create integration test task
val integrationTest = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    
    useJUnitPlatform()
    
    shouldRunAfter("test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Make check task depend on integration tests
tasks.named("check") {
    dependsOn(integrationTest)
}

// Test configuration
tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}
checkstyle {
    toolVersion = "10.12.1"
    configFile = file("config/checkstyle/checkstyle.xml")
    maxWarnings = 0
}

tasks.withType<Checkstyle> {
    reports {
        xml.required.set(false)
        html.required.set(true)
    }
    // Fail the build if there are any Checkstyle violations
    ignoreFailures = false
}

// Configure existing JaCoCo coverage verification - fails build if coverage is not 100%
tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)
	violationRules {
		rule {
			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = BigDecimal("1.0")
			}
		}
		rule {
			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = BigDecimal("1.0")
			}
		}
		rule {
			limit {
				counter = "CLASS"
				value = "COVEREDRATIO"
				minimum = BigDecimal("1.0")
			}
		}
		rule {
			limit {
				counter = "METHOD"
				value = "COVEREDRATIO"
				minimum = BigDecimal("1.0")
			}
		}
	}
	classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/dto/**", 
                    "**/config/**",
                    "**/*Application.class",
                    "**/generated/**"
                )
            }
        })
    )
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport, tasks.jacocoTestCoverageVerification)
}
