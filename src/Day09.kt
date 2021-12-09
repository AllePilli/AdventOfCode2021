fun main() {
    fun List<List<Int>>.getLowPoints(): List<Pair<Int, Int>> = indices.flatMap { rowIdx ->
        first().indices.mapNotNull { colIdx ->
            this[rowIdx][colIdx]
                .takeIf { value ->
                    adjacentValuesOf(rowIdx, colIdx).all { adjacentValue -> adjacentValue > value }
                }
                ?.let { rowIdx to colIdx }
        }
    }
    
    fun part1(heightMap: List<List<Int>>): Int = heightMap.getLowPoints().sumOf { (rowIdx, colIdx) ->
        1 + heightMap[rowIdx][colIdx]
    }
    
    fun List<List<Int>>.getBasinOf(point: Pair<Int, Int>): Set<Pair<Int, Int>> = buildSet {
        add(point)
        val potentialEdgePoints = mutableListOf(point)
        
        while (potentialEdgePoints.isNotEmpty()) {
            val currentPoint = potentialEdgePoints.removeLast()
            
            adjacentPositionsOf(currentPoint.first, currentPoint.second)
                .filter { (rowIdx, colIdx) ->
                    rowIdx in this@getBasinOf.indices && colIdx in this@getBasinOf.first().indices
                }
                .filterNot { (rowIdx, colIdx) -> this@getBasinOf[rowIdx][colIdx] == 9 }
                .filterNot { potentialEdgePoint ->
                    potentialEdgePoint in this || potentialEdgePoint in potentialEdgePoints
                }
                .forEach { potentialEdgePoint ->
                    potentialEdgePoints.add(potentialEdgePoint)
                    add(potentialEdgePoint)
                }
        }
    }
    
    fun part2(heightMap: List<List<Int>>): Int = heightMap.getLowPoints()
        .map { lowPoint -> heightMap.getBasinOf(lowPoint).size }
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc * i }
    
    fun List<String>.prepareInput(): List<List<Int>> = map { line -> line.map(Char::digitToInt) }
    
    readInput("Day09_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 15)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 1134)
        }
    }
    
    readInput("Day09").prepareInput().let { input ->
        part1(input).let {
            check(it == 539)
            println("Output Part 1: $it")
        }
        part2(input).let {
            check(it == 736920)
            println("Output Part 2: $it")
        }
    }
}