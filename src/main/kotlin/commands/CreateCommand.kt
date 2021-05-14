package commands

import utils.FileManipulator
import cryptography.Algorithms
import kotlinx.cli.*
import utils.Dialog

class CreateCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {
    override fun execute() {
        manipulator = FileManipulator(filename)

        println("Creating DB in file $filename")
        if (manipulator!!.fileExists()) {
            print("File already exists. Rewrite? [y/n] ")
            var answer: String? = ""
            while (answer != "n" && answer != "y" && answer != null) {
                answer = readLine()
            }
            if (answer == "n" || answer == null) {
                println("Aborted by user, exiting.")
                return
            }
        }

        val author = Dialog.ask("author's name")
        val password = Dialog.ask("password phrase")

        manipulator!!.createFile(password.toByteArray(), author, Algorithms.AES128CBC)
        println("File successfully created! Do not forget to memorize the password phrase!")
    }
}
