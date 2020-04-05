package com.kneelawk.modpackattributor

import com.kneelawk.modpackattributor.curse.AddonUtils
import com.kneelawk.modpackattributor.curse.ModpackFile
import com.kneelawk.modpackattributor.data.curseapi.AddonData
import com.kneelawk.modpackattributor.data.curseapi.AuthorData
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Created by Kneelawk on 4/4/20.
 */
class ModpackAttributorController : Controller() {
    val modpackLocation = stringProperty("")
    val running = booleanProperty(false)
    val generationProgress = doubleProperty(0.0)
    val generationStatus = stringProperty("Not Started.")
    val attributionText = stringProperty("")

    private val addonUtils: AddonUtils by inject()

    private var previousDir = File(System.getProperty("user.home"))

    fun selectModpackLocation() {
        chooseFile(
            "Select a Modpack File",
            arrayOf(FileChooser.ExtensionFilter("Curse Modpack Files", listOf("*.zip", "*.bin"))),
            previousDir,
            FileChooserMode.Single
        ).firstOrNull()?.let {
            modpackLocation.value = it.absolutePath
            previousDir = it.parentFile
        }
    }

    fun generateAttribution() {
        val modpackStr = modpackLocation.value
        if (modpackStr.isBlank()) {
            generationStatus.value = "Modpack Location field is empty."
            return
        }

        val modpackPath = Paths.get(modpackStr)
        if (!Files.exists(modpackPath)) {
            generationStatus.value = "Modpack file does not exist."
            return
        }

        running.value = true
        attributionText.value = ""
        val task = generateAttributionTask(modpackPath)
        generationProgress.bind(task.progressProperty())
        generationStatus.bind(task.messageProperty())

        task.finally {
            running.value = false
            generationProgress.unbind()
            generationStatus.unbind()
        }
    }

    fun generateAttributionTask(modpackPath: Path) = runAsync {
        updateProgress(-1, -1)
        updateMessage("Parsing Modpack.")

        val modpackFile = ModpackFile(modpackPath)
        val manifest = modpackFile.readManifest()

        val fileCount = manifest.files.size.toLong()

        updateProgress(0, fileCount)
        updateMessage("Getting info... 0 / $fileCount")

        val addons = arrayListOf<AddonData>()
        var loadedAddons = 0L

        manifest.files.forEach { fileJson ->
            val addon = addonUtils.getAddonInfo(fileJson.projectID)
            if (addon != null) {
                addons += addon
                loadedAddons++
            } else {
                println("Unable to retrieve addon information for projectId: ${fileJson.projectID}")
            }
            updateProgress(loadedAddons, fileCount)
            updateMessage("Getting info... $loadedAddons / $fileCount")
        }

        updateMessage("Complete. $loadedAddons / $fileCount")

        addons.sortBy { it.name }

        val accumulatedMessage = "## Mod Attribution\n" + addons.joinToString("") { getAttributionMarkdown(it) }

        runLater {
            attributionText.value = accumulatedMessage
        }
    }

    fun getAttributionMarkdown(addon: AddonData) =
        " * ${getAddonNameString(addon)} by ${getAuthorListString(addon.authors)}\n"

    fun getAddonNameString(addon: AddonData) = addon.websiteURL?.let { "[${addon.name}]($it)" } ?: addon.name

    fun getAuthorListString(authors: List<AuthorData>): String {
        if (authors.isEmpty()) {
            return "Unknown author"
        }

        if (authors.size == 1) {
            return getAuthorString(authors[0])
        }

        if (authors.size == 2) {
            return "${getAuthorString(authors[0])} and ${getAuthorString(authors[1])}"
        }

        var authorsString = ""
        for (i in 0 until (authors.size - 1)) {
            authorsString += "${getAuthorString(authors[i])}, "
        }
        authorsString += "and ${getAuthorString(authors[authors.size - 1])}"
        return authorsString
    }

    fun getAuthorString(author: AuthorData) = author.url?.let { "[${author.name}]($it)" } ?: author.name
}
