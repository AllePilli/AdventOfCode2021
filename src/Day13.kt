fun main() {
    val positionRgx by lazy { """(\d+),(\d+)""".toRegex() }
    val foldRgx by lazy { """fold along ([xy])=(\d+)""".toRegex() }
    
    fun fold(coordinate: Int, foldCoordinate: Int): Int {
        val distanceToFold = coordinate - foldCoordinate
        return foldCoordinate - distanceToFold
    }
    
    fun doFolds(startDots: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): List<Pair<Int, Int>> =
        folds.fold(startDots) { dots, (foldAxis, foldCoordinate) ->
            dots.map { (x, y) ->
                val newX = if (foldAxis == "x" && x > foldCoordinate) fold(x, foldCoordinate) else x
                val newY = if (foldAxis == "y" && y > foldCoordinate) fold(y, foldCoordinate) else y
        
                newX to newY
            }
        }
    
    fun part1(startDots: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Int =
        doFolds(startDots, listOf(folds.first())).distinct().size
    
    fun part2(startDots: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): Int {
        val dots = doFolds(startDots, folds)
        
        val xRange = dots.minOf { it.first }..dots.maxOf { it.first }
        val yRange = dots.minOf { it.second }..dots.maxOf { it.second }
    
        xRange.map { x -> yRange.map { y ->  if (x to y in dots) "#" else "." } }
            .transposed()
            .map { row -> row.joinToString("") { it } }
            .forEach(::println)
        
        return 1
    }
    
    fun List<String>.prepareInput(): Pair<List<Pair<Int, Int>>, List<Pair<String, Int>>> {
        val startDots = mapNotNull { line ->
            positionRgx.find(line)
                ?.destructured
                ?.let { (x, y) -> x.toInt() to y.toInt() }
        }
    
        val folds = mapNotNull { line ->
            foldRgx.find(line)
                ?.destructured
                ?.let { (type, coordinate) -> type to coordinate.toInt() }
        }
        
        return startDots to folds
    }
    
    readInput("Day13_test").prepareInput().let { testInput ->
        part1(testInput.first, testInput.second).let {
            println("Test Part 1: $it")
            check(it == 17)
        }
        part2(testInput.first, testInput.second).let {
            println("Test Part 2: $it\n")
            check(it == 1)
        }
    }
    
    readInput("Day13").prepareInput().let { input ->
        part1(input.first, input.second).let {
            check(it == 671)
            println("Output Part 1: $it")
        }
        part2(input.first, input.second).let {
            check(it == 1)
            println("Output Part 2: $it")
        }
    }
}