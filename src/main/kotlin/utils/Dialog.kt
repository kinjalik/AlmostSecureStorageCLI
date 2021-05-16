package utils

object Dialog {
    fun ask(name: String): String {
        var res: String? = null
        while (res == null) {
            print("Type $name: ")
            res = readLine()
        }
        return res
    }

    fun warnLatestVersion(latest: Metadata.GitHubRelease) {
        val res = "Almost Secure Storage CLI ${latest.tag_name} available!\n" +
            "Download: ${latest.url}\n"
        println(res)
    }
}