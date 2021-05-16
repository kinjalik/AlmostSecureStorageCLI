package commands

import utils.FileManipulator
import exceptions.InvalidFileException
import kotlinx.cli.*
import utils.Dialog

class ReadInfoCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {

    override fun execute() {
        super.execute()

        val password = Dialog.ask(msgs.getString("dialog.askPassword"))

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            println("${msgs.getString("dialog.Author")}: ${storage.author}")
            println("${msgs.getString("dialog.numOfEntities")}: ${storage.entities.size}")
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}
