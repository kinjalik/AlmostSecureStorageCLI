package commands

import exceptions.EntityNotFoundException
import exceptions.InvalidFileException
import kotlinx.cli.ArgType
import kotlinx.cli.ArgumentValueDelegate
import utils.Dialog

class SetPropertyCommand(
    name: String,
    desc: String,
    fileDelegate: ArgumentValueDelegate<String>
): Command(name, desc, fileDelegate) {
    private val entryName by argument(ArgType.String, "entryName", "Name of the data entity")
    private val propName by argument(ArgType.String, "propName", "Name of the property to change")
    private val propNewValue by argument(ArgType.String, "propNewValues", "New value of property")

    override fun execute() {
        super.execute()
        val password = Dialog.ask("password phrase")

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            val entity = storage.getEntity(password.toByteArray(), entryName)
            val curProps = entity.properties.toMutableMap()
            curProps[propName] = propNewValue
            storage.updateEntity(password.toByteArray(), entryName, entryName, curProps)
            manipulator!!.writeFile(storage, password.toByteArray())

        } catch (e: InvalidFileException) {
            println(e.message)
        } catch (e: EntityNotFoundException) {
            println("No such data entity found.")
        }
    }
}