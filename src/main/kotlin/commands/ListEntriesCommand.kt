package commands

import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import utils.FileManipulator

@OptIn(ExperimentalCli::class)
class ListEntriesCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("ls", "Get list of entries in database") {
    private val filename: String by filenameDelegate
    override fun execute() {
        val manipulator = FileManipulator(filename)
        if (!manipulator.fileExists()) {
            println("Unable to add new entry: File doesn't exists.")
            return
        }

        var password: String? = null
        while (password == null) {
            println("Type password phrase:")
            password = readLine()
        }

        try {
            val storage = manipulator.readFile(password.toByteArray())
            storage.entities.forEach { println("Entity: ${it.name}") }
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}