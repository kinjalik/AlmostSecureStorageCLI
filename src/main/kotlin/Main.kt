import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val parser = ArgParser("AlmostSecureStorage")
    val filename by parser.argument(ArgType.String, description = "Database file")

    class Create : Subcommand("create", "Create new database") {
        override fun execute() {
            println("Creating DB!")
        }
    }

    class ReadInfo : Subcommand("db-info", "Get information about database") {
        override fun execute() {
            println("Get db-info!")
        }
    }

    parser.subcommands(Create(), ReadInfo())
    parser.parse(args)
}