# Format Console Output

<p style="font-size:20px; "> Make your console output more readable by adding borders and tables. </p>

## Functions

<p style="font-size:22px; "> frame </p>

Prints out the string with a border around it.

```
fun String.frame()
```

Prints out the list with a border around it. By default, the text is aligned left.
To center the text or align it right, set `align` to `Align.MIDDLE` or `Align.RIGHT` respectively.

```
fun List<String>.frame(align: Align = Align.LEFT)
```

<p style="font-size:22px; "> table </p>

Turns the list into a single column table with a header and prints it out.
By default, the text is aligned left.
To center the text or align it right, set `align` to `Align.MIDDLE` or `Align.RIGHT` respectively.

```
fun List<String>.table(header: String, align: Align = Align.LEFT)
```

Joins any number of lists into a table and prints out the results.
If `header` is set to `true`, the 1st string of every list becomes a header.
If `total` is set to `true`, the last string of every list becomes a part of a total line.
If `border` is set to `false`, the table cell borders become transparent.
By default, the text is aligned left.
To center the text or align it right, set `align` to `Align.MIDDLE` or `Align.RIGHT` respectively.

```
fun table(
    vararg columns: MutableList<String>,
    header: Boolean = false,
    total: Boolean = false,
    border: Boolean = true,
    align: Align = Align.LEFT
)
```

<p style="font-size:22px; "> toStringList </p>

Returns a mutable list of type `String`.
Pass a string as an argument, to add it to the beginning of the new list.
Leave the parentheses empty to leave the list as is.

```
fun <T> mutableList<T>.toStringList(header: String = "none"): MutableList<String>
```

## Examples

### A string with a border around it

```
val text = "Making my way downtown"
text.frame()
```

![A string with a border around it](/readme_img/frame_string.png)

### A single column table with a header, centered text

```
val list = listOf("eggs", "bread", "oranges", "tea", "watermelon")
list.table("Groceries", "middle")
```

![A single column table with a header, centered text](/readme_img/table_groceries.png)

### A 3-column table with a header, aligned left

```
val list1 = mutableListOf("fruits", "oranges", "apples", "strawberries", "grapes")
val list2 = mutableListOf("vegetables", "tomatoes", "potatoes", "onions")
val list3 = mutableListOf("dairy", "butter", "cheese")
table(list1, list2, list3)
```

![A 3-column table with a header, aligned left](/readme_img/table_fruits.png)

### A 5-column table with a header and a total row, centered text

```
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
```

![A 5-column table with a header and a total row, centered text](/readme_img/table_product.png)