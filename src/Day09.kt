fun main() {
    fun part1(list: List<String>): Int {
        val heightMap = list.map { line -> line.map(Char::digitToInt) }
        
//        with(list.map { line -> line.map(Char::digitToInt) }) {
//            indices.flatMap { rowIdx ->
//                first().indices.mapNotNull { colIdx ->
//                    this[rowIdx][colIdx].takeIf { value ->
//                        adjacentValuesOf(rowIdx, colIdx).all { adjacentValue -> adjacentValue > value }
//                    }
//                }
//            }.sumOf { 1 + it }
//        }
    
        return heightMap.indices
            .flatMap { rowIdx ->
                heightMap.first().indices.mapNotNull { colIdx ->
                    heightMap[rowIdx][colIdx].takeIf { value ->
                        heightMap.adjacentValuesOf(rowIdx, colIdx)
                            .all { adjacentValue -> adjacentValue > value }
                    }
                }
            }
            .sumOf { 1 + it }
    }
    
    fun part2(list: List<String>): Int = 1
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day09_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 15)
        }
        part2(testInput).let {
            println("Test Part 2: $it")
            check(it == 1)
        }
    }
    
    readInput("Day09").prepareInput().let { input ->
        part1(input).let {
            //check(it == 1)
            println("Output Part 1: $it")
        }
        part2(input).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}