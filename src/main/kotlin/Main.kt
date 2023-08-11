import kotlin.math.ceil

fun main() {
    println("I. Border around String:")
    val text = "Making my way downtown"
    text.frame()

    println("II. Border around List<String>, align = right:")
    val list = listOf("eggs", "bread", "oranges", "tea", "watermelon")
    list.frame("right")

    println("III. Single column table with header, align = middle:")
    list.table("Groceries", "middle")

    println("IV. Table with 3 columns:")
    val list1 = mutableListOf("fruits", "oranges", "apples", "strawberries", "grapes")
    val list2 = mutableListOf("vegetables", "tomatoes", "potatoes", "onions")
    val list3 = mutableListOf("dairy", "butter", "cheese")
    table(list1, list2, list3)

    println("V. Table with 4 columns and header:")
    val list4 = mutableListOf("snacks", "popcorn", "nuts")
    table(list1, list2, list3, list4, header = true)

    println("VI. Table with 5 columns, header, total, align = middle:")
    val l0 = mutableListOf(1,2,3,4,5,6,7,8)
    val l0new = mutableListOf<String>()
    for (num in l0) l0new.add(num.toString())
    val l1 = mutableListOf("Product", "Chocolate", "Gummibarchen", "Scottish Longbreads", "Sir Rodney's Scones", "Tarte au sucre", "Chocolate Biscuits", "Total")
    val l2 = mutableListOf(744.60, 5079.60, 1267.50, 1418.00, 4728.00, 943.89, 14181.59)
    val l2new = mutableListOf("Qtr 1")
    for (num in l2) l2new.add(num.toString())
    val l3 = mutableListOf(162.56, 1249.20, 1062.50, 756.00, 4547.92, 349.60, 8127.78)
    val l3new = mutableListOf("Qtr 2")
    for (num in l3) l3new.add(num.toString())
    val l4 = mutableListOf(907.16, 6328.80, 2330.00, 2174.00, 9275.92, 1293.49, 22309.37)
    val l4new = mutableListOf("Grand Total")
    for (num in l4) l4new.add(num.toString())
    table(l0new, l1, l2new, l3new, l4new, header = true, total = true, align = "middle")
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
* If `header` is set to `true`, the 1st string of every list becomes a header.
* By default, the text in the table is aligned left.
* To center the text, set `align` to `middle`.
* To align the text right, set `align` to `right`.
*/
fun table( vararg columns: MutableList<String>,
           header: Boolean = false,
           total: Boolean = false,
           align: String = "left") {
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
    border  += '+'
    println(border)
    for (i in 0..<numOfRows){
        print("| ")
        for (j in columns.indices) {
            columns[j][i].printCell(longestWords[j], align)
            print(" | ")
        }
        println()
        if (header && i == 0) println(border)
        if (total && (i == numOfRows - 2)) {
            val ttlBorder = border.replace('=', '-')
            println(ttlBorder)
        }
    }
    println(border)
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