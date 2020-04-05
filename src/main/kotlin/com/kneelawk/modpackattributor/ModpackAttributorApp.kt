package com.kneelawk.modpackattributor

import tornadofx.App
import tornadofx.Rest
import tornadofx.importStylesheet
import tornadofx.launch

fun main(args: Array<String>) {
    launch<ModpackAttributorApp>(args)
}

class ModpackAttributorApp : App(ModpackAttributorView::class) {
    init {
        importStylesheet(javaClass.getResource("obsidian/obsidian.css").toExternalForm())
        importStylesheet(javaClass.getResource("style.css").toExternalForm())
        Rest.useApacheHttpClient()
    }
}
