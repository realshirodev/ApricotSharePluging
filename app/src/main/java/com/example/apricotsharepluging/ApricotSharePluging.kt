package com.example.apricotsharepluging

/*
    Desarrollado por David shiro Beltran
    Fecha 02/01/2024
    Update ---------
*/

import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import java.io.File

class ApricotSharePluging(godot: Godot) : GodotPlugin(godot) {
    override fun getPluginName(): String = "ApricotSharePluging"

    @UsedByGodot
    fun apricotShareText(title: String, subject: String, text: String): Boolean {
        if (text.isBlank()) return false
        val hostActivity = activity ?: return false

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_TITLE, title)
        }
        return showShareChooser(hostActivity, shareIntent, title)
    }

    @UsedByGodot
    fun apricotShareImg(pathImg: String, title: String, subject: String, text: String): Boolean {
        val hostActivity = activity ?: return false
        val file = File(pathImg)
        if (!file.isFile) {
            Log.e(TAG, "Share image does not exist: $pathImg")
            return false
        }

        val uri = try {
            FileProvider.getUriForFile(
                hostActivity,
                "${hostActivity.packageName}.apricotshare.fileprovider",
                file
            )
        } catch (error: IllegalArgumentException) {
            Log.e(TAG, "Share image is outside the configured provider paths: $pathImg", error)
            return false
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_TITLE, title)
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        return showShareChooser(hostActivity, shareIntent, title)
    }

    private fun showShareChooser(
        hostActivity: android.app.Activity,
        shareIntent: Intent,
        title: String
    ): Boolean {
        val chooser = Intent.createChooser(shareIntent, title)
        runOnHostThread {
            try {
                hostActivity.startActivity(chooser)
            } catch (error: Exception) {
                Log.e(TAG, "Unable to open Android's share chooser", error)
            }
        }
        return true
    }

    private companion object {
        const val TAG = "ApricotSharePluging"
    }
}