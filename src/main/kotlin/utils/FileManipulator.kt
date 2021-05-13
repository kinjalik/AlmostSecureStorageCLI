import cryptography.Algorithms
import exceptions.InvalidFileException
import operationalComponents.StorageData
import java.io.*

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

    fun readFile(password: ByteArray): StorageData {
        if (!fileExists())
            throw FileNotFoundException("File $filename not found")

        val fr = FileInputStream(File(filename))
        val fileBytes = fr.readAllBytes()
            ?: throw InvalidFileException("Zero bytes was read. Possibly, the file is corrupted.")

        val filePrefix = fileBytes.slice(prefix.indices)
        if (filePrefix != prefix)
            throw InvalidFileException("File prefix is incorrect.")
        val filePostfix = fileBytes.slice(fileBytes.size-1 downTo fileBytes.size - postfix.size)
        if (filePostfix.reversed() != postfix)
            throw InvalidFileException("File postfix is incorrect.")

        val rawData = fileBytes.slice(prefix.size until fileBytes.size - postfix.size)
        try {
            return StorageData.read(password, rawData.toByteArray())
        } catch (e: Exception) {
            throw InvalidFileException("Failed to read stored data. Probably, the password is incorrect.")
        }
    }

    fun fileExists() = File(filename).exists()
}