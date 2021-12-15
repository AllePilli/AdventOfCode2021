import kotlin.math.min

fun main() {
    fun part1(list: List<List<Int>>): Int {
        val destination = list.size to list.first().size
        val cache = hashMapOf<Pair<Int, Int>, Int>()
        
        fun findPath(x: Int, y: Int): Int = list[x][y] + if (x to y == destination) 0 else {
            if (x == destination.first - 1) cache.computeIfAbsent(x to y + 1) { (newX, newY) ->
                (newY until destination.second).sumOf { itY -> list[newX][itY] }
            } else if (y == destination.second - 1) cache.computeIfAbsent(x + 1 to y) { (newX, newY) ->
                (newX until destination.first).sumOf { itX -> list[itX][newY] }
            } else {
                val left = cache[x + 1 to y] ?: findPath(x + 1, y).also { cache[x + 1 to y] = it }
                val right = cache[x to y + 1] ?: findPath(x, y + 1).also { cache[x to y + 1] = it }
                min(left, right)
            }
        }
        
        return findPath(0, 0) - list[0][0]
    }
    
    fun part2(list: List<List<Int>>): Int = 1
    
    fun List<String>.prepareInput(): List<List<Int>> = map { line -> line.map(Char::digitToInt) }
    
    readInput("Day15_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 40)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 1)
        }
    }
    
    readInput("Day15").prepareInput().let { input ->
        part1(input).let {
            check(it == 447)
            println("Output Part 1: $it")
        }
        part2(input).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}