import java.util.*

fun main() {
    val openChars = listOf('(', '[', '{', '<')
    val closeChars = listOf(')', ']', '}', '>')
    val points = closeChars.zip(listOf(3L, 57L, 1197L, 25137L)).toMap()
    
    fun part1(list: List<String>): Long = list.sumOf { line ->
        val stack = Stack<Char>()
        fun Char.notCorrectClosing(): Boolean = openChars.indexOf(stack.pop()) != closeChars.indexOf(this)
        
        line.fold(0L) { score, c ->
            score + when (c) {
                in openChars -> {
                    stack.push(c)
                    0
                }
                else -> if (stack.isEmpty() || c.notCorrectClosing()) points[c]!! else 0
            }
        }
    }
    
    fun part2(list: List<String>): Int = 1
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day10_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 26397L)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 288957)
        }
    }
    
    readInput("Day10").prepareInput().let { input ->
        part1(input).let {
            check(it == 343863L)
            println("Output Part 1: $it")
        }
        part2(input).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}