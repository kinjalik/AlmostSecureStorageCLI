package commands

import exceptions.EntityNotFoundException
import exceptions.InvalidFileException
import kotlinx.cli.ArgType
import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import operationalComponents.StorageData
import utils.Dialog
import utils.FileManipulator

class GetEntryCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {
    private val entryName by argument(ArgType.String, "entryName", "Name of the data entity")

    override fun execute() {
        super.execute()
        val password = Dialog.ask("password phrase")

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            val e = storage.getEntity(password.toByteArray(), entryName)
            println("Entity ${e.name}")
            for (prop in e.properties) {
                println("${prop.key}: ${prop.value}")
            }
        } catch (e: InvalidFileException) {
            println(e.message)
        } catch (e: EntityNotFoundException) {
            println("No such data entity found.")
        }
    }
}