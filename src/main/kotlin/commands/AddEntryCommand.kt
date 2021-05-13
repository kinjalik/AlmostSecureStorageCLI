package commands

import utils.FileManipulator
import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import utils.Dialog

@OptIn(ExperimentalCli::class)
class AddEntryCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("add-entry", "Add new entry into database") {
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
            val key = password.toByteArray()
            val storage = manipulator.readFile(key)

            val entityName = Dialog.ask("Entity name")
            val numOfParams = Dialog.ask("Number of parameters").toInt()

            val props = mutableMapOf<String, String>()
            for (i in 1..numOfParams) {
                val propName = Dialog.ask("Property name")
                val propValue = Dialog.ask("Property value")
                props[propName] = propValue
            }
            storage.addEntity(key, entityName, props)
            manipulator.writeFile(storage, key)
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}