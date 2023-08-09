import kotlin.math.ceil

fun main() {
    val text = "Making my way downtown"
    println("I. Border around String:")
    text.frame()
    val list = listOf("eggs", "bread", "oranges", "tea", "watermelon")
    println("II. Border around List<String>:")
    list.frame()
    println("III. Border around List<String> aligned right:")
    list.frame("right")
    println("IV. Single column table:")
    list.table()
    println("V. Single column table with header, centered text:")
    list.table("Groceries", "middle")
}

/*
* Prints out the string with a border around it.
*/
fun String.frame() {
    val border = makeBorder(this)
    println(border)
    println("| $this |")
    println(border)
}

/*
* Prints out the list with a border around it.
* The list is arranged into a column.
* By default, the text in the column is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
* */
fun List<String>.frame(align: String = "left") {
    var longestWord = this[0]
    for (string in this) if (longestWord.length < string.length) longestWord = string
    val border = makeBorder(longestWord)
    println(border)
    for (string in this)  {
        string.printLine(longestWord, align)
    }
    println(border)
}

/*
* Turns the list into a single column table.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun List<String>.table(align: String = "left") {
    var longestWord = this[0]
    for (string in this) if (longestWord.length < string.length) longestWord = string
    val border = makeBorder(longestWord)
    println(border)
    for (string in this)  {
        string.printLine(longestWord, align)
        println(border)
    }
}

/*
* Turns the list into a single column table with a header.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun List<String>.table(header: String, align: String = "left") {
    var longestWord = header
    for (string in this) if (longestWord.length < string.length) longestWord = string
    val border = makeBorder(longestWord)
    println(border)
    header.printLine(longestWord, align)
    println(border)
    for (string in this) string.printLine(longestWord, align)
    println(border)
}

/*
* Creates and returns a horizontal border for tables and frames.
*/
fun makeBorder(longestWord: String): String {
    var border = "+-"
    for (i in 1..longestWord.length) border += '-'
    border += "-+"
    return border
}

/*
* Prints out a row of a table or a frame.
*/
fun String.printLine(longestWord: String, align: String) {
    val emptySpace = longestWord.length - this.length
    print("| ")
    when (align) {
        "right" -> {
            for (i in 1..emptySpace) print(' ')
            print(this)
        }
        "middle" -> {
            val right = emptySpace/2
            val left = ceil(emptySpace/2.0).toInt()
            for (i in 1..right) print(' ')
            print(this)
            for (i in 1..left) print(' ')
        }
        else -> {
            print(this)
            for (i in 1..emptySpace) print(' ')
        }
    }
    println(" |")
}