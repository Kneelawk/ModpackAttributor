package com.kneelawk.modpackattributor.curse

import com.kneelawk.modpackattributor.data.curseapi.AddonJson
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.toModel

/**
 * Used to get information about addons.
 */
class AddonUtils : Controller() {
    companion object {
        const val GET_ADDON_INFO = "https://addons-ecs.forgesvc.net/api/v2/addon/%d"
    }

    val rest: Rest by inject()

    fun getAddonInfo(projectID: Long): AddonJson? {
        val response = rest.get(GET_ADDON_INFO.format(projectID))
        return if (response.status == Rest.Response.Status.OK) {
            response.one().toModel()
        } else {
            null
        }
    }
}