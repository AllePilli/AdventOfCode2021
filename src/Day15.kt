import kotlin.math.min

fun main() {
    fun findPath(list: List<List<Int>>, x: Int, y: Int, endX: Int, endY: Int, cache: HashMap<Pair<Int, Int>, Int>): Int
    = list[x][y] + if (x == endX && y == endY) 0 else {
        if (x == endX - 1) cache.computeIfAbsent(x to y + 1) { (newX, newY) ->
            (newY until endY).sumOf { itY -> list[newX][itY] }
        } else if (y == endY - 1) cache.computeIfAbsent(x + 1 to y) { (newX, newY) ->
            (newX until endX).sumOf { itX -> list[itX][newY] }
        } else {
            val left = cache[x + 1 to y]
                ?: findPath(list, x + 1, y, endX, endY, cache).also { cache[x + 1 to y] = it }
            val right = cache[x to y + 1]
                ?: findPath(list, x, y + 1, endX, endY, cache).also { cache[x to y + 1] = it }
            min(left, right)
        }
    }
    
    fun part1(list: List<List<Int>>): Int =
        findPath(list, 0, 0, list.size, list.first().size, hashMapOf()) - list[0][0]
    
    fun part2(list: List<List<Int>>): Int =
        findPath(list, 0, 0, list.size, list.first().size, hashMapOf()) - list[0][0]
    
    fun List<String>.prepareInput(): List<List<Int>> = map { line -> line.map(Char::digitToInt) }
    
    fun List<List<Int>>.prepareInputPart2(): List<List<Int>> {
        val otherChunks: List<List<List<Int>>> = buildList {
            add(this@prepareInputPart2)
            for (i in 1..8) add(
                this@prepareInputPart2.map { row ->
                    row.map { value -> (value + i).mod(9).takeIf { it != 0 } ?: 9 }
                }
            )
        }
    
        val ySize = first().size
        val newYRange = 0 until (first().size * 5)
    
        return (0 until (size * 5)).map { x ->
            val xChunkIdx = x / size
            val chunkX = x.mod(size)
            newYRange.map { y ->
                val yChunkIdx = y / ySize
                val chunkIdx = (xChunkIdx + yChunkIdx).mod(otherChunks.size)
                val chunkY = y.mod(ySize)
                
                otherChunks[chunkIdx][chunkX][chunkY]
            }
        }
    }
    
    readInput("Day15_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 40)
        }
        part2(testInput.prepareInputPart2()).let {
            println("Test Part 2: $it\n")
            check(it == 315)
        }
    }

    readInput("Day15").prepareInput().let { input ->
        part1(input).let {
            check(it == 447)
            println("Output Part 1: $it")
        }
        part2(input.prepareInputPart2()).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}