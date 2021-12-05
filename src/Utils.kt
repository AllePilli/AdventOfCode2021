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
 * Iterate columns of a 2D list
 */
inline fun <T> List<List<T>>.forEachCol(action: (List<T>) -> Unit): Unit = indices
    .map { idx -> map { it[idx] } }
    .forEach(action)

infix fun String.getGroups(regex: Regex): List<String>? = regex.find(this)
    ?.groupValues
    ?.drop(1)
