package commands

import exceptions.EntityNotFoundException
import exceptions.InvalidFileException
import kotlinx.cli.ArgType
import kotlinx.cli.ArgumentValueDelegate
import utils.Dialog

class DeletePropertyCommand(
    name: String,
    desc: String,
    fileDelegate: ArgumentValueDelegate<String>
): Command(name, desc, fileDelegate) {
    private val entryName by argument(ArgType.String, "entryName", "Name of the data entity")
    private val propName by argument(ArgType.String, "propName", "Name of the property to change")

    override fun execute() {
        super.execute()
        val password = Dialog.ask(msgs.getString("dialog.askPassword"))

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            val entity = storage.getEntity(password.toByteArray(), entryName)
            val curProps = entity.properties.toMutableMap()
            curProps.remove(propName)
            storage.updateEntity(password.toByteArray(), entryName, entryName, curProps)

        } catch (e: InvalidFileException) {
            println(e.message)
        } catch (e: EntityNotFoundException) {
            println(msgs.getString("error.entityNotFound") + ".")
        }
    }
}