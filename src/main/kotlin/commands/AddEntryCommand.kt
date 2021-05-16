package commands

import exceptions.InvalidFileException
import kotlinx.cli.ArgumentValueDelegate
import utils.Dialog

class AddEntryCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {
    override fun execute() {
        super.execute()
        if (!manipulator!!.fileExists()) {
            return
        }
        val password = Dialog.ask(msgs.getString("dialog.askPassword"))

        try {
            val key = password.toByteArray()
            val storage = manipulator!!.readFile(key)

            val entityName = Dialog.ask(msgs.getString("dialog.askEntityName"))
            val numOfParams = Dialog.ask(msgs.getString("dialog.askNumOfParams")).toInt()

            val props = mutableMapOf<String, String>()
            for (i in 1..numOfParams) {
                val propName = Dialog.ask(msgs.getString("dialog.askPropertyName"))
                val propValue = Dialog.ask(msgs.getString("dialog.askPropertyValue"))
                props[propName] = propValue
            }
            storage.addEntity(key, entityName, props)
            manipulator!!.writeFile(storage, key)
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}