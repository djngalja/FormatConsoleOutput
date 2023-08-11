import kotlin.math.ceil

fun main() {
    val text = "Making my way downtown"
    println("I. Border around String:")
    text.frame()

    val list = listOf("eggs", "bread", "oranges", "tea", "watermelon")
    println("II. Border around List<String>, align = right:")
    list.frame("right")
    println("III. Single column table with header, align = middle:")
    list.table("Groceries", "middle")

    val list1 = mutableListOf("eggs", "bread", "oranges", "tea", "watermelon")
    val list2 = mutableListOf("trousers", "shirt", "socks", "tie")
    val list3 = mutableListOf("cream", "mascara", "toner")
    println("IV. Table with 3 columns, align = left:")
    table(list1, list2, list3)
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
        print("| ")
        string.printCell(longestWord, align)
        println(" |")
    }
    println(border)
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
    print("| ")
    header.printCell(longestWord, align)
    println(" |")
    println(border)
    for (string in this) {
        print("| ")
        string.printCell(longestWord, align)
        println(" |")
    }
    println(border)
}

/*
* Joins any number of lists into a table and prints out the results.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun table(vararg columns: MutableList<String>, align: String = "left") {
    var numOfRows = 0
    val longestWords = mutableListOf<String>()
    for (column in columns) {
        if (numOfRows < column.size) numOfRows = column.size // the number of rows in the table is the size of the longest list
        var temp = ""
        for (row in column) if (temp.length < row.length) temp = row // find the widest word in the list
        longestWords.add(temp)
    }
    for (column in columns) while (column.size < numOfRows) column.add("") //all lists should have the same size
    var border = ""
    for (longestWord in longestWords) {
        border += makeBorder(longestWord)
        border = border.dropLast(1)
    }
    println("$border+")
    for (i in 0..<numOfRows){
        print("| ")
        for (j in columns.indices) {
            columns[j][i].printCell(longestWords[j], align)
            print(" | ")
        }
        println()
    }
    println("$border+")
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
* Prints out a cell of a table or a frame.
*/
fun String.printCell(longestWord: String, align: String) {
    val emptySpace = longestWord.length - this.length
    when (align) {
        "right" -> {
            for (i in 1..emptySpace) print(' ')
            print(this)
        }
        "middle", "centre", "center" -> {
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
}