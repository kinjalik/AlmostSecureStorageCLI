import commands.CreateCommand
import commands.GetEntryCommand
import commands.ListEntriesCommand
import commands.ReadInfoCommand
import commands.AddEntryCommand
import commands.DeleteEntryCommand
import commands.DeletePropertyCommand
import commands.SetPropertyCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import utils.Dialog
import utils.Metadata

@OptIn(ExperimentalCli::class)
suspend fun main(args: Array<String>) {
    val meta = Metadata()
    try {
        /*
        We don't care about concurrence, because message about new version should be displayed BEFORE further exec
         */
        val latest = meta.fetchLatestVersion()
        if (latest != null && latest.tag_name != "v" + meta.version)
            Dialog.warnLatestVersion(latest)
    } catch (e: Throwable) {
        println(e)
    }

    val parser = ArgParser("AlmostSecureStorage")
    val filenameDelegate = parser.argument(ArgType.String, "filename", description = "Database file")
    parser.subcommands(
        CreateCommand("create", "Create new database", filenameDelegate.delegate),
        ReadInfoCommand("db-info", "Fetch an information about the database", filenameDelegate.delegate),
        ListEntriesCommand("ls", "List all entries in database", filenameDelegate.delegate),
        GetEntryCommand("get-entry", "Fetch entry content from database", filenameDelegate.delegate),
        AddEntryCommand("add-entry", "Add new entry into the database", filenameDelegate.delegate),
        DeleteEntryCommand("del-entry", "Delete entry from database", filenameDelegate.delegate),
        SetPropertyCommand("set-prop", "Set new value of entry's property", filenameDelegate.delegate),
        DeletePropertyCommand("del-prop", "Delete entry's property", filenameDelegate.delegate)
    )

    parser.parse(args)
}
