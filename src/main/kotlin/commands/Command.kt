package commands

import kotlinx.cli.ArgumentValueDelegate
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import utils.FileManipulator
import java.util.ResourceBundle

@OptIn(ExperimentalCli::class)
abstract class Command(
    name: String,
    description: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Subcommand(name, description) {
    protected val filename: String by filenameDelegate
    protected var manipulator: FileManipulator? = null
    protected val msgs = ResourceBundle.getBundle("messages")

    override fun execute() {
        manipulator = FileManipulator(filename)

        if (!manipulator!!.fileExists()) {
            println("Unable to add new entry: File doesn't exists.")
            return
        }
    }
}