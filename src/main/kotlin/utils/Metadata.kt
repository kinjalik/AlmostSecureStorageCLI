package utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.jar.Attributes
import java.util.jar.Manifest

class Metadata {
    var version: String?
    var repository: String?
    init {
        val mfStream = javaClass.classLoader.getResourceAsStream("META-INF/MANIFEST.MF")
        val mf = Manifest()
        try {
            mf.read(mfStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val attr = mf.mainAttributes
        version = attr.getValue(Attributes.Name.IMPLEMENTATION_VERSION)
        repository = attr.getValue("Git-Repository")
    }
    val url = "https://api.github.com/repos/$repository/releases/latest"

    suspend fun fetchLatestVersion(): GitHubRelease? {
        if (!checkConnection()) {
            return null
        }

        val client = HttpClient(CIO)
        val response: String = client.get(url)

        val res = Json { ignoreUnknownKeys = true }.decodeFromString<GitHubRelease>(response)

        client.close()
        client.close()

        return res
    }

    @Serializable
    data class GitHubRelease(
        val id: Int,
        val tag_name: String,
        @SerialName("html_url")
        val url: String
    )

    private fun checkConnection() : Boolean {
        try {
            val req = URL(url)
            val conn = req.openConnection()
            conn.connect()
        } catch (e: IOException) {
            return false
        } catch (e: MalformedURLException) {
            return false
        }
        return true
    }
}