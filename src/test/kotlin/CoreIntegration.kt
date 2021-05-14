import cryptography.Algorithms
import operationalComponents.StorageData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CoreIntegration {
    @Test
    fun `Draft test for ensuring`() {
        val authorName = "Albert Akmukhametov"
        val password = "Some kind of password".toByteArray()
        val entityName = "TEST ENTITY"
        val props = HashMap<String, String>()
        props["PARAM1"] = "A"
        props["PARAM 2"] = "B"

        val originalStorage = StorageData(authorName, Algorithms.AES128CBC)
        originalStorage.addEntity(password, entityName, props)

        val raw = originalStorage.getResult(password)

        val readStorage = StorageData.read(password, raw)
        val readEntity = readStorage.getEntity(password, entityName)

        assertEquals(readStorage.author, authorName)
        assertEquals(readEntity.properties, props)
    }
}