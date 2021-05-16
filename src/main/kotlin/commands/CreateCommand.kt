package commands

import utils.FileManipulator
import cryptography.Algorithms
import kotlinx.cli.*
import utils.Dialog
import java.util.ResourceBundle

class CreateCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {

    override fun execute() {
        manipulator = FileManipulator(filename)

        println("${msgs.getString("create.announce")} $filename")
        if (manipulator!!.fileExists()) {
            val accept = msgs.getString("app.accept")
            val decline = msgs.getString("app.decline")
            print("${msgs.getString("create.existsMessage")} [$accept/$decline] ")
            var answer: String? = ""
            while (answer != accept && answer != decline && answer != null) {
                answer = readLine()
            }
            if (answer == decline || answer == null) {
                println(msgs.getString("create.abortedByUser"))
                return
            }
        }

        val author = Dialog.ask(msgs.getString("dialog.askAuthor"))
        val password = Dialog.ask(msgs.getString("dialog.askPassword"))

        manipulator!!.createFile(password.toByteArray(), author, Algorithms.AES128CBC)
        println(msgs.getString("create.success"))
    }
}
