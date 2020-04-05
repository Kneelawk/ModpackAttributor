package com.kneelawk.modpackattributor.data.curseapi

import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject

/**
 * A single addon retrieved from curse.
 */
data class AddonJson(

    /**
     * The project id of this addon.
     */
    override var id: Long = 0,

    /**
     * The name of this addon.
     */
    override var name: String = "",

    /**
     * A list of authors that worked on this addon.
     */
    override var authors: List<AuthorJson> = emptyList(),

    /**
     * a list of attachments (often images) associated with this addon.
     */
    override var attachments: List<AttachmentJson> = emptyList(),

    /**
     * The url of this addon on Curse's website.
     */
    override var websiteURL: String? = null,

    /**
     * The id of the game (Minecraft, WoW, etc.) this addon is associated with (Minecraft is 432).
     */
    override var gameID: Long = 432,

    /**
     * A brief summary describing this addon.
     */
    override var summary: String? = null,

    /**
     * The file id of the current default file for this addon.
     */
    override var defaultFileID: Long? = null,

    /**
     * A list of addon categories (Tech, Magic, Aesthetic, etc.) this addon is associated with.
     */
    override var categories: List<CategoryJson> = emptyList(),

    /**
     * The category id of the primary category this addon is associated with.
     */
    override var primaryCategoryID: Long? = null,

    /**
     * The overall category section (Mod, Modpack, Bukkit Plugin, etc.) this addon is part of.
     */
    override var categorySection: CategorySectionJson? = null,

    /**
     * A machine-readable name used to refer to this addon.
     */
    override var slug: String? = null,

    /**
     * The last time this addon was modified.
     */
    override var dateModified: LocalDateTime = LocalDateTime.now(),

    /**
     * When this addon was first created on the curse servers.
     */
    override var dateCreated: LocalDateTime = LocalDateTime.now(),

    /**
     * When this addon was made public.
     */
    override var dateReleased: LocalDateTime? = null,

    /**
     * Whether this addon is available.
     */
    override var isAvailable: Boolean = true,

    /**
     * Whether this addon is considered 'experimental'.
     */
    override var isExperiemental: Boolean = false
) : JsonModel, AddonData {

    /**
     * Serializes this addon to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("name", name)
            add("authors", authors.toJSON())
            add("attachments", attachments)
            websiteURL?.let { add("websiteUrl", it) }
            add("gameId", gameID)
            summary?.let { add("summary", it) }
            defaultFileID?.let { add("defaultFileId", it) }
            add("categories", categories.toJSON())
            primaryCategoryID?.let { add("primaryCategoryId", it) }
            categorySection?.let { add("categorySection", it) }
            slug?.let { add("slug", it) }
            add("dateModified", dateModified)
            add("dateCreated", dateCreated)
            add("dateReleased", dateReleased)
            add("isAvailable", isAvailable)
            add("isExperiemental", isExperiemental)
        }
    }

    /**
     * Loads this addon from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")!!
            name = string("name")!!
            authors = jsonArray("authors")?.toModel() ?: emptyList()
            attachments = jsonArray("attachments")?.toModel() ?: emptyList()
            websiteURL = string("websiteUrl")
            gameID = long("gameId") ?: 432
            summary = string("summary")
            defaultFileID = long("defaultFileId")
            categories = jsonArray("categories")?.toModel() ?: emptyList()
            primaryCategoryID = long("primaryCategoryId")
            categorySection = jsonObject("categorySection")?.toModel()
            slug = string("slug")
            dateModified = LocalDateTime.parse(string("dateModified")!!, DateTimeFormatter.ISO_DATE_TIME)
            dateCreated = LocalDateTime.parse(string("dateCreated")!!, DateTimeFormatter.ISO_DATE_TIME)
            dateReleased = string("dateRelease")?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
            isAvailable = boolean("isAvailable") ?: true
            isExperiemental = boolean("isExperiemental") ?: false
        }
    }
}

/**
 * Describes an attachment associated with an addon.
 */
data class AttachmentJson(

    /**
     * The attachment id of this attachment.
     */
    override var id: Long? = null,

    /**
     * The project id of the project this attachment is associated with.
     */
    override var projectID: Long? = null,

    /**
     * This attachment's description.
     */
    override var description: String = "",

    /**
     * Whether this is the default attachment for its associated project.
     */
    override var isDefault: Boolean = false,

    /**
     * The url of the thumbnail for this attachment.
     */
    override var thumbnailURL: String? = null,

    /**
     * The title of this attachment.
     */
    override var title: String? = null,

    /**
     * The url of this attachment.
     */
    override var url: String = ""
) : JsonModel, AttachmentData {

    /**
     * Serializes this attachment descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            id?.let { add("id", it) }
            projectID?.let { add("projectId", it) }
            add("description", description)
            add("isDefault", isDefault)
            thumbnailURL?.let { add("thumbnailUrl", it) }
            title?.let { add("title", it) }
            add("url", url)
        }
    }

    /**
     * Loads this attachment descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")
            projectID = long("projectId")
            description = string("description") ?: ""
            isDefault = boolean("isDefault") ?: false
            thumbnailURL = string("thumbnailUrl")
            title = string("title")
            url = string("url")!!
        }
    }
}

/**
 * Contains identifiers for an addon author.
 */
data class AuthorJson(

    /**
     * The username of this author.
     */
    override var name: String = "",

    /**
     * The url of this author's user page on the Curse website.
     */
    override var url: String? = null,

    /**
     * The Curse user id of this author.
     */
    override var userID: Long? = null,

    /**
     * The Twitch user id of this author.
     */
    override var twitchID: Long? = null
) : JsonModel, AuthorData {

    /**
     * Serializes this author descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("name", name)
            url?.let { add("url", it) }
            userID?.let { add("userId", it) }
            twitchID?.let { add("twitchId", it) }
        }
    }

    /**
     * Loads this author descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name")!!
            url = string("url")
            userID = long("userId")
            twitchID = long("twitchId")
        }
    }
}

/**
 * Describes a category an addon can fit into.
 *
 * Some potential categories are things like: Tech, Magic, Aesthetic, and others.
 */
data class CategoryJson(

    /**
     * The category id of this category.
     */
    override var categoryID: Long = 0,

    /**
     * The name of this category.
     */
    override var name: String = "",

    /**
     * The url of the page on Curse's website for this category.
     */
    override var url: String? = null,

    /**
     * The url of the icon for this category.
     */
    override var avatarURL: String? = null,

    /**
     * The category id of the parent category of this category.
     */
    override var parentID: Long? = null,

    /**
     * The category id of the root category of this category.
     */
    override var rootID: Long? = null,

    /**
     * The game id of the game this category is associated with.
     */
    override var gameID: Long = 432
) : JsonModel, CategoryData {

    /**
     * Serializes this category descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("categoryId", categoryID)
            add("name", name)
            url?.let { add("url", it) }
            avatarURL?.let { add("avatarUrl", it) }
            parentID?.let { add("parentId", it) }
            rootID?.let { add("rootId", it) }
            add("gameId", gameID)
        }
    }

    /**
     * Loads this category descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            categoryID = long("categoryId")!!
            name = string("name")!!
            url = string("url")
            avatarURL = string("avatarUrl")
            parentID = long("parentId")
            rootID = long("rootId")
            gameID = long("gameId") ?: 432
        }
    }
}

/**
 * Represents an overarching category section.
 *
 * Some examples of category sections are: Mods, Modpacks, Bukkit Plugins, and others.
 */
data class CategorySectionJson(

    /**
     * The category section id of this category section.
     */
    override var id: Long = 0,

    /**
     * The game id of the game this category section is associated with.
     */
    override var gameID: Long = 432,

    /**
     * The name of this category section.
     */
    override var name: String = "",

    /**
     * The packaging type used for this category section.
     */
    override var packageType: Long = 0,

    /**
     * The category id of this category section.
     */
    override var gameCategoryID: Long = 0
) : JsonModel, CategorySectionData {

    /**
     * Serializes this catetory section descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("gameId", gameID)
            add("name", name)
            add("packageType", packageType)
            add("gameCategoryId", gameCategoryID)
        }
    }

    /**
     * Loads this category section descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")!!
            gameID = long("gameId") ?: 432
            name = string("name")!!
            packageType = long("packageType")!!
            gameCategoryID = long("gameCategoryId")!!
        }
    }
}