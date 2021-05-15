package utils

import java.io.IOException
import java.util.jar.Attributes
import java.util.jar.Manifest

class Version {
    fun ver(): String? {
        val mfStream = javaClass.classLoader.getResourceAsStream("META-INF/MANIFEST.MF")
        val mf = Manifest()
        try {
            mf.read(mfStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val attrs = mf.mainAttributes
        return attrs.getValue(Attributes.Name.IMPLEMENTATION_VERSION)

    }
}