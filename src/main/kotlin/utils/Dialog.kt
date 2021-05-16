package utils

import java.util.ResourceBundle

object Dialog {
    val messages = ResourceBundle.getBundle("messages")

    fun ask(name: String): String {
        var res: String? = null
        while (res == null) {
            print("${messages.getString("dialog.askToType")} $name: ")
            res = readLine()
        }
        return res
    }

    fun warnLatestVersion(latest: Metadata.GitHubRelease) {
        val res = "${messages.getString("app.name")} ${latest.tag_name} ${messages.getString("dialog.available")}!\n" +
            "${messages.getString("dialog.downloadCall")}: ${latest.url}\n"
        println(res)
    }
}