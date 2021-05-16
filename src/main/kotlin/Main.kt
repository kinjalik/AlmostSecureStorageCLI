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
import kotlinx.coroutines.runBlocking
import utils.Dialog
import utils.Metadata
import java.util.ResourceBundle

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val meta = Metadata()
    try {
        /*
        We don't care about concurrence, because message about new version should be displayed BEFORE further exec
         */
        val latest = runBlocking { meta.fetchLatestVersion() }
        if (latest != null && latest.tag_name != "v" + meta.version)
            Dialog.warnLatestVersion(latest)
    } catch (e: Throwable) {
    }
    val msgs = ResourceBundle.getBundle("messages")

    val parser = ArgParser("AlmostSecureStorage")
    val filenameDelegate = parser.argument(ArgType.String, "filename", description = msgs.getString("desc.filename"))

    parser.subcommands(
        CreateCommand("create", msgs.getString("desc.create"), filenameDelegate.delegate),
        ReadInfoCommand("db-info", msgs.getString("desc.db-info"), filenameDelegate.delegate),
        ListEntriesCommand("ls", msgs.getString("desc.ls"), filenameDelegate.delegate),
        GetEntryCommand("get-entry", msgs.getString("desc.get-entry"), filenameDelegate.delegate),
        AddEntryCommand("add-entry", msgs.getString("desc.add-entry"), filenameDelegate.delegate),
        DeleteEntryCommand("del-entry", msgs.getString("desc.del-entry"), filenameDelegate.delegate),
        SetPropertyCommand("set-prop", msgs.getString("desc.set-prop"), filenameDelegate.delegate),
        DeletePropertyCommand("del-prop", msgs.getString("desc.del-prop"), filenameDelegate.delegate)
    )

    parser.parse(args)
}
