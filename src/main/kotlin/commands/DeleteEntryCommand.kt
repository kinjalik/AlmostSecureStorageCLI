package commands

import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import utils.Dialog

class DeleteEntryCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {
    override fun execute() {
        val password = Dialog.ask("password phrase")

        try {
            val entryName = Dialog.ask("name of entry to delete")
            val key = password.toByteArray()
            val storage = manipulator!!.readFile(key)
            storage.deleteEntity(entryName)
            manipulator!!.writeFile(storage, key)
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}