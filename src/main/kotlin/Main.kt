import commands.*
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val parser = ArgParser("AlmostSecureStorage")

    val filenameDelegate = parser.argument(ArgType.String, "filename", description = "Database file")
    parser.subcommands(
        CreateCommand(filenameDelegate.delegate),
        ReadInfoCommand(filenameDelegate.delegate),
        GetEntryCommand(filenameDelegate.delegate),
        ListEntriesCommand(filenameDelegate.delegate),
        AddEntryCommand(filenameDelegate.delegate)
    )

    parser.parse(args)
}
