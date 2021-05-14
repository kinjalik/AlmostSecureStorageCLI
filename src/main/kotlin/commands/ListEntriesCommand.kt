package commands

import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import utils.Dialog
import utils.FileManipulator

class ListEntriesCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {

    override fun execute() {
        super.execute()
        val password = Dialog.ask("password phrase")

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            storage.entities.forEach { println("Entity: ${it.name}") }
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}