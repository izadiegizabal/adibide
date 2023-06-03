package xyz.izadi.adibide.convention

import org.gradle.api.artifacts.VersionConstraint
import java.util.Optional

fun Optional<VersionConstraint>.getInt(): Int = get().toString().toInt()
fun Optional<VersionConstraint>.getString(): String = get().toString()
