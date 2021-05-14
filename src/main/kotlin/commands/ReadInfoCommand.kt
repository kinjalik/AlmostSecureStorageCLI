package commands

import utils.FileManipulator
import exceptions.InvalidFileException
import kotlinx.cli.*

class ReadInfoCommand(
    name: String,
    desc: String,
    filenameDelegate: ArgumentValueDelegate<String>
) : Command(name, desc, filenameDelegate) {

    override fun execute() {
        super.execute()

        var password: String? = null
        while (password == null) {
            print("Type password phrase: ")
            password = readLine()
        }

        try {
            val storage = manipulator!!.readFile(password.toByteArray())
            println("Author: ${storage.author}")
            println("Number of entities: ${storage.entities.size}")
        } catch (e: InvalidFileException) {
            println(e.message)
        }
    }
}
