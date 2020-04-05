package com.kneelawk.modpackattributor.data.manifest

import tornadofx.JsonBuilder

/**
 * Immutable interface describing a modpack manifest.
 */
interface ManifestData {
    val minecraft: MinecraftData
    val manifestType: String
    val manifestVersion: Long
    val name: String
    val version: String
    val author: String
    val projectID: Long?
    val files: List<FileData>
    val overrides: String
    val additionalJavaArgs: String?

    fun toJSON(json: JsonBuilder)
}

/**
 * Immutable interface describing a mod dependency of a modpack.
 */
interface FileData {
    val projectID: Long
    val fileID: Long
    val required: Boolean

    fun toJSON(json: JsonBuilder)
}

/**
 * Immutable interface describing minecraft information for a modpack manifest.
 */
interface MinecraftData {
    val version: String
    val modLoaders: List<ModLoaderData>

    fun toJSON(json: JsonBuilder)
}

/**
 * Immutable interface describing a mod loader object in a modpack manifest.
 */
interface ModLoaderData {
    val id: String
    val primary: Boolean

    fun toJSON(json: JsonBuilder)
}
