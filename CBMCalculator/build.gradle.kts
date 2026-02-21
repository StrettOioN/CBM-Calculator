buildscript {
    val agp_version by extra("8.0.2")
    val agp_version1 by extra("7.3.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
