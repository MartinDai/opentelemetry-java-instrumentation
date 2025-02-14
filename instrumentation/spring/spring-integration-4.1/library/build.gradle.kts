plugins {
  id("otel.library-instrumentation")
}

dependencies {
  compileOnly("com.google.auto.value:auto-value-annotations")
  annotationProcessor("com.google.auto.value:auto-value")

  library("org.springframework.integration:spring-integration-core:4.1.0.RELEASE")

  testImplementation(project(":instrumentation:spring:spring-integration-4.1:testing"))

  testLibrary("org.springframework.boot:spring-boot-starter-test:1.5.22.RELEASE")
  testLibrary("org.springframework.boot:spring-boot-starter:1.5.22.RELEASE")
  testLibrary("org.springframework.cloud:spring-cloud-stream:2.2.1.RELEASE")
  testLibrary("org.springframework.cloud:spring-cloud-stream-binder-rabbit:2.2.1.RELEASE")
}

tasks {
  withType<Test>().configureEach {
    systemProperty("testLatestDeps", findProperty("testLatestDeps") as Boolean)
    usesService(gradle.sharedServices.registrations["testcontainersBuildService"].service)
  }
}

configurations.testRuntimeClasspath {
  resolutionStrategy {
    // requires old logback (and therefore also old slf4j)
    force("ch.qos.logback:logback-classic:1.2.11")
    force("org.slf4j:slf4j-api:1.7.36")
  }
}
