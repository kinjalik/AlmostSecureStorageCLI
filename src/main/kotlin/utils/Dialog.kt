package utils

object Dialog {
    fun ask(name: String): String {
        var res: String? = null
        while (res == null) {
            print("Type $name: ")
            res = readLine()
        }
        return res
    }
}