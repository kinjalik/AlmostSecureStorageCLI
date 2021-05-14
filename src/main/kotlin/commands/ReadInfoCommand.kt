package commands

import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class ReadInfoCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("db-info", "Get information about database") {
    private val filename: String by filenameDelegate
    override fun execute() {
        println("Read info rom $filename")
    }
}
