import kotlin.math.max
import kotlin.math.min

fun main() {
    val lineRgx = """(\d+),(\d+)\s+->\s+(\d+),(\d+)""".toRegex()
    
    fun countOverlapping(lines: List<List<Int>>, includeDiagonal: Boolean = false): Int = lines
        .mapNotNull { (x1, y1, x2, y2) ->
            if (x1 == x2) (min(y1, y2)..max(y1, y2)).map { y -> x1 to y }
            else if (y1 == y2) (min(x1, x2)..max(x1, x2)).map { x -> x to y1 }
            else if (includeDiagonal){
                if (x1 < x2) {
                    if (y1 < y2) (x1..x2).zip(y1..y2)
                    else (x1..x2).zip(y1 downTo y2)
                } else {
                    if (y1 < y2) (x1 downTo x2).zip(y1..y2)
                    else (x1 downTo x2).zip(y1 downTo y2)
                }
            }
            else null
        }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }
    
    fun part1(lines: List<List<Int>>): Int = countOverlapping(lines)
    
    fun part2(lines: List<List<Int>>): Int = countOverlapping(lines, true)
    
    val testInput = readInput("Day05_test").map { textLine ->
        textLine.getGroups(lineRgx)!!.map(String::toInt)
    }
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)
    
    val input = readInput("Day05").map { textLine ->
        textLine.getGroups(lineRgx)!!.map(String::toInt)
    }
    part1(input).let {
        check(it == 7468)
        println(it)
    }
    part2(input).let {
        check(it == 22364)
        println(it)
    }
}