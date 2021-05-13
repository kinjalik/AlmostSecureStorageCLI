package commands

import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class CreateCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("create", "Create new database") {
    private val filename: String by filenameDelegate
    override fun execute() {
        println("Creating DB in file $filename")
    }
}
