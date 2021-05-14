package commands

import utils.FileManipulator
import cryptography.Algorithms
import kotlinx.cli.*

@OptIn(ExperimentalCli::class)
class CreateCommand(filenameDelegate: ArgumentValueDelegate<String>) : Subcommand("create", "Create new database") {
    private val filename: String by filenameDelegate

    override fun execute() {
        println("Creating DB in file $filename")
        val manipulator = FileManipulator(filename)
        if (manipulator.fileExists()) {
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
        var author: String? = null
        while (author == null) {
            print("Type author's name: ")
            author = readLine()
        }

        var password: String? = null
        while (password == null) {
            println("Type password phrase and remember it!")
            password = readLine()
        }

        manipulator.createFile(password.toByteArray(), author, Algorithms.AES128CBC)
        println("File successfully created! Do not forget to memorize the password phrase!")
    }
}
