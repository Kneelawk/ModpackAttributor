package com.kneelawk.modpackattributor

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.Priority
import tornadofx.*

/**
 * Main view for the Modpack Attributor.
 */
class ModpackAttributorView : View("Modpack Attributor") {
    private val control: ModpackAttributorController by inject()

    init {
        with(primaryStage) {
            width = 1280.0
            height = 800.0
            minWidth = 500.0
            minHeight = 400.0
        }
    }

    override val root = vbox {
        padding = insets(25.0)
        spacing = 10.0
        hbox {
            spacing = 10.0
            alignment = Pos.CENTER
            disableProperty().bind(control.running)
            label("Modpack Location:")
            textfield(control.modpackLocation) {
                hgrow = Priority.ALWAYS
            }
            button("...") {
                action {
                    control.selectModpackLocation()
                }
            }
        }
        button("Generate Attribution") {
            maxWidth = Double.MAX_VALUE
            disableProperty().bind(control.running)
            action {
                control.generateAttribution()
            }
        }
        label(control.generationStatus)
        progressbar(control.generationProgress) {
            maxWidth = Double.MAX_VALUE
            control.generationProgress.addListener { _, _, newValue ->
                if (newValue.toDouble() >= 1.0) {
                    addPseudoClass("done")
                } else {
                    removePseudoClass("done")
                }
            }
        }
        textarea(control.attributionText) {
            vgrow = Priority.ALWAYS
            isEditable = false
        }
    }
}