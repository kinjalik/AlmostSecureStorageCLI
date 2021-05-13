package commands

import utils.FileManipulator
import exceptions.InvalidFileException
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class ReadInfoCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("db-info", "Get information about database") {
    private val filename: String by filenameDelegate
    override fun execute() {
        val manipulator = FileManipulator(filename)
        if (!manipulator.fileExists()) {
            println("File $filename not found.")
            return
        }

        var password: String? = null
        while (password == null) {
            print("Type password phrase: ")
            password = readLine()
        }

        try {
            val storage = manipulator.readFile(password.toByteArray())
            println("Author: ${storage.author}")
            println("Number of entities: ${storage.entities.size}")
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}
