// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

plugins {
  java
  alias(libs.plugins.spotbugs)
}

repositories {
  mavenCentral()
}

group = "com.example"
version = "1.0.0"

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

dependencies {
  compileOnly(libs.jspecify)
  compileOnly(libs.jetbrains.annotations)
  
  annotationProcessor(platform(libs.immutables.bom))
  annotationProcessor(libs.immutables.value)
  compileOnly(platform(libs.immutables.bom))
  compileOnly(libs.bundles.immutables)
  
  spotbugs(libs.spotbugs)
}

spotbugs {
  effort.set(com.github.spotbugs.snom.Effort.MAX)
  reportLevel.set(com.github.spotbugs.snom.Confidence.LOW)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
  reports.create("html") {
    required.set(true)
  }
}
