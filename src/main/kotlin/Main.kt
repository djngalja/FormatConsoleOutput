import kotlin.math.ceil

enum class Align { RIGHT, MIDDLE, LEFT }

fun main() {
    println("I. Border around String:")
    val text = "Making my way downtown"
    text.frame()

    println("II. Border around List<String>, right-aligned:")
    val list = listOf("eggs", "bread", "oranges", "tea", "watermelon")
    list.frame(Align.RIGHT)

    println("III. Single column table with header, centered text:")
    list.table("Groceries", Align.MIDDLE)

    println("IV. Table with 3 columns:")
    val list1 = mutableListOf("fruits", "oranges", "apples", "strawberries", "grapes")
    val list2 = mutableListOf("vegetables", "tomatoes", "potatoes", "onions")
    val list3 = mutableListOf("dairy", "butter", "cheese")
    table(list1, list2, list3)

    println("V. Table with 4 columns and header:")
    val list4 = mutableListOf("snacks", "popcorn", "nuts")
    table(list1, list2, list3, list4, header = true)

    println("VI. Table with 5 columns, header, total, centered text:")
    val l0 = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
    val l1 = mutableListOf(
        "Product",
        "Chocolate",
        "Gummibarchen",
        "Scottish Longbreads",
        "Sir Rodney's Scones",
        "Tarte au sucre",
        "Chocolate Biscuits",
        "Total"
    )
    val l2 = mutableListOf(744.60, 5079.60, 1267.50, 1418.00, 4728.00, 943.89, 14181.59)
    val l3 = mutableListOf(162.56, 1249.20, 1062.50, 756.00, 4547.92, 349.60, 8127.78)
    val l4 = mutableListOf(907.16, 6328.80, 2330.00, 2174.00, 9275.92, 1293.49, 22309.37)
    table(
        l0.toStringList(),
        l1,
        l2.toStringList("Qtr 1"),
        l3.toStringList("Qtr 2"),
        l4.toStringList("Grand Total"),
        header = true,
        total = true,
        align = Align.MIDDLE
    )
    println("VII. Table with 5 columns and header, without border:")
    table(
        l0.toStringList(),
        l1,
        l2.toStringList("Qtr 1"),
        l3.toStringList("Qtr 2"),
        l4.toStringList("Grand Total"),
        header = true,
        border = false
    )
}

/*
* Returns a mutable list of type String.
* Pass a string as an argument, to add it to the beginning of the new list.
* Leave the parentheses empty to leave the list as is.
*/
fun <T> MutableList<T>.toStringList(header: String = "none"): MutableList<String> {
    val newList = if (header == "none") mutableListOf() else mutableListOf(header)
    for (t in this) newList.add(t.toString())
    return newList
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
fun List<String>.frame(align: Align = Align.LEFT) {
    var longestWord = this[0]
    for (string in this) if (longestWord.length < string.length) longestWord = string
    val border = makeBorder(longestWord)
    println(border)
    for (string in this) {
        print("| ")
        string.printCell(longestWord, align)
        println(" |")
    }
    println(border)
}

/*
* Turns the list into a single column table with a header and prints it out.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun List<String>.table(header: String, align: Align = Align.LEFT) {
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
* If `header` is set to `true`, the 1st string of every list becomes a header.
* If `total` is set to `true`, the last string of every list becomes a part of a total line.
* If `border` is set to `false`, the table cell borders become transparent.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun table(
    vararg columns: MutableList<String>,
    header: Boolean = false,
    total: Boolean = false,
    border: Boolean = true,
    align: Align = Align.LEFT
) {
    var numOfRows = 0
    val longestWords = mutableListOf<String>()
    for (column in columns) {
        if (numOfRows < column.size) numOfRows =
            column.size // the number of rows in the table is the size of the longest list
        var temp = ""
        for (row in column) if (temp.length < row.length) temp = row // find the widest (=longest) word in the list
        longestWords.add(temp)
    }
    for (column in columns) while (column.size < numOfRows) column.add("") //all lists should have the same size
    var line = ""
    if (border) {
        for (longestWord in longestWords) {
            line += makeBorder(longestWord)
            line = line.dropLast(1)
        }
        line += '+'
    }
    println(line)
    for (i in 0..<numOfRows) {
        if (border) print("| ")
        else print(" ")
        for (j in columns.indices) {
            columns[j][i].printCell(longestWords[j], align)
            if (border) print(" | ")
            else print("  ")
        }
        println()
        if (header && i == 0) println(line)
        if (total && (i == numOfRows - 2)) {
            val ttlLine = line.replace('=', '-')
            println(ttlLine)
        }
    }
    println(line)
}

/*
* Creates and returns a horizontal border for tables and frames.
*/
fun makeBorder(longestWord: String, char: Char = '='): String {
    var border = "+$char"
    for (i in 1..longestWord.length) border += char
    border += "$char+"
    return border
}

/*
* Prints out a cell of a table or a frame.
*/
fun String.printCell(longestWord: String, align: Align) {
    val emptySpace = longestWord.length - this.length
    when (align) {
        Align.RIGHT -> {
            for (i in 1..emptySpace) print(' ')
            print(this)
        }

        Align.MIDDLE -> {
            val right = emptySpace / 2
            val left = ceil(emptySpace / 2.0).toInt()
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