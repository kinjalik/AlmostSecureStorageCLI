package commands

import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import utils.Dialog

class DeleteEntryCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {
    override fun execute() {
        val password = Dialog.ask(msgs.getString("dialog.askPassword"))

        try {
            val entryName = Dialog.ask(msgs.getString("dialog.askEntityName"))
            val key = password.toByteArray()
            val storage = manipulator!!.readFile(key)
            storage.deleteEntity(entryName)
            manipulator!!.writeFile(storage, key)
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}