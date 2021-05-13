import cryptography.Algorithms
import operationalComponents.StorageData
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter

class FileManipulator(val filename: String) {
    // Magic Bytes Sequence
    val prefix = listOf<Byte>(0x70, 0x6c, 0x65, 0x61, 0x73, 0x65, 0x5f, 0x63, 0x6c, 0x6f, 0x73, 0x65, 0x5f, 0x69, 0x74)
    val postfix = prefix

    fun createFile(password: ByteArray, author: String, encAlgorithms: Algorithms) {
        val storage = StorageData(author, encAlgorithms)
        val storageSerialized = storage.getResult(password)
        val rawData = prefix.toMutableList()
        rawData.addAll(storageSerialized.toList())
        rawData.addAll(prefix)

        val fw = FileOutputStream(File(filename))
        fw.write(rawData.toByteArray())
        fw.flush()
        fw.close()
    }

    fun fileExists() = File(filename).exists()
}