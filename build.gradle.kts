allprojects {
  group = "dev.fritz2"
  version = "1.0"
}

subprojects {
  repositories {
    mavenCentral()
  }

  extra.set("serializationVersion", "1.3.1")
  extra.set("fritz2Version", "1.0-RC1")
}

plugins {
  val kotlinVersion = "1.7.20"
  val springBootVersion = "2.7.6"
  val springBootDependencyManagementVersion = "1.0.15.RELEASE"
  kotlin("multiplatform") version kotlinVersion apply false
  kotlin("js") version kotlinVersion apply false
  kotlin("jvm") version kotlinVersion apply false
  kotlin("plugin.spring") version kotlinVersion apply false
  kotlin("plugin.jpa") version kotlinVersion apply false
  kotlin("plugin.serialization") version kotlinVersion apply false
  id("org.springframework.boot") version springBootVersion apply false
  id("io.spring.dependency-management") version springBootDependencyManagementVersion apply false
  id("com.google.devtools.ksp") version "1.7.20-1.0.6" apply false
}

