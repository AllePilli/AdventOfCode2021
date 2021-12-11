import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Runs the [action] on each element in a 2D [Iterable]
 */
inline fun <T> Iterable<Iterable<T>>.forEachElement(action: (T) -> Unit): Unit = forEach { row ->
    row.forEach(action)
}

/**
 * Returns true if at least one element in this 2D [Iterable] matches the [predicate]
 */
inline fun <T> Iterable<Iterable<T>>.anyElement(predicate: (T) -> Boolean): Boolean = any { it.any(predicate) }

/**
 * Returns the first element matching the [predicate], returns null if no elements match the [predicate]
 */
inline fun <T> Iterable<Iterable<T>>.findElement(predicate: (T) -> Boolean): T? {
    var element: T?
    
    for (row in this) {
        element = row.find(predicate)
        if (element != null) return element
    }
    
    return null
}

/**
 * Iterate columns of a 2D list
 */
inline fun <T> List<List<T>>.forEachCol(action: (List<T>) -> Unit): Unit = indices
    .map { idx -> map { it[idx] } }
    .forEach(action)

/**
 * Returns the adjacent (UP, DOWN, LEFT, RIGHT) coordinates of a 2D point
 */
fun adjacentPositionsOf(rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> = listOf(
    rowIdx - 1 to colIdx,
    rowIdx + 1 to colIdx,
    rowIdx to colIdx - 1,
    rowIdx to colIdx + 1
)

/**
 * Returns the surrounding (UP, DOWN, LEFT, RIGHT, DIAGONAL) coordinates of a 2D point
 */
fun surroundingPositionsOf(rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> = buildList {
    addAll(adjacentPositionsOf(rowIdx, colIdx))
    add(rowIdx - 1 to colIdx + 1)
    add(rowIdx + 1 to colIdx + 1)
    add(rowIdx + 1 to colIdx - 1)
    add(rowIdx - 1 to colIdx - 1)
}

/**
 * Returns the adjacent (UP, DOWN, LEFT, RIGHT) values 2D point
 */
fun <T> List<List<T>>.adjacentValuesOf(rowIdx: Int, colIdx: Int): List<T> = adjacentPositionsOf(rowIdx, colIdx)
    .mapNotNull { (row, col) ->
        try { this[row][col] } catch (ignored: IndexOutOfBoundsException) { null }
    }

/**
 * Returns the surrounding (UP, DOWN, LEFT, RIGHT, DIAGONAL) values of a 2D point
 */
fun <T> List<List<T>>.surroundingValuesOf(rowIdx: Int, colIdx: Int): List<T> = surroundingPositionsOf(rowIdx, colIdx)
    .mapNotNull { (row, col) ->
        try { this[row][col] } catch (ignored: IndexOutOfBoundsException) { null }
    }

infix fun String.getGroups(regex: Regex): List<String>? = regex.find(this)
    ?.groupValues
    ?.drop(1)
