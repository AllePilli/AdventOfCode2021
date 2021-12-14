fun main() {
    val positionRgx by lazy { """(\d+),(\d+)""".toRegex() }
    val foldRgx by lazy { """fold along ([xy])=(\d+)""".toRegex() }
    
    fun part1(list: List<String>): Int {
        var dots = list.mapNotNull { line ->
            positionRgx.find(line)
                ?.destructured
                ?.let { (x, y) -> x.toInt() to y.toInt() }
        }
        
        val (foldAxis, foldCoordinate) = list.firstNotNullOf { line ->
            foldRgx.find(line)
                ?.destructured
                ?.let { (type, coordinate) -> type to coordinate.toInt() }
        }
    
        dots = if (foldAxis == "x") dots.map { (x, y) ->
            val newX = if (x > foldCoordinate) {
                val distanceToFold = x - foldCoordinate
                foldCoordinate - distanceToFold
            } else x
    
            newX to y
        } else dots.map { (x, y) ->
            val newY = if (y > foldCoordinate) {
                val distanceToFold = y - foldCoordinate
                foldCoordinate - distanceToFold
            } else y
    
            x to newY
        }
        
        return dots.distinct().size
    }
    
    fun part2(list: List<String>): Int {
        var dots = list.mapNotNull { line ->
            positionRgx.find(line)
                ?.destructured
                ?.let { (x, y) -> x.toInt() to y.toInt() }
        }
    
        val folds = list.mapNotNull { line ->
            foldRgx.find(line)
                ?.destructured
                ?.let { (type, coordinate) -> type to coordinate.toInt() }
        }
        
        folds.forEach { (foldAxis, foldCoordinate) ->
            dots = if (foldAxis == "x") dots.map { (x, y) ->
                val newX = if (x > foldCoordinate) {
                    val distanceToFold = x - foldCoordinate
                    foldCoordinate - distanceToFold
                } else x
        
                newX to y
            } else dots.map { (x, y) ->
                val newY = if (y > foldCoordinate) {
                    val distanceToFold = y - foldCoordinate
                    foldCoordinate - distanceToFold
                } else y
        
                x to newY
            }
        }
        
        val xRange = dots.minOf { it.first }..dots.maxOf { it.first }
        val yRange = dots.minOf { it.second }..dots.maxOf { it.second }
    
        xRange.map { x -> yRange.map { y ->  if (x to y in dots) "#" else "." } }
            .transposed()
            .map { row -> row.joinToString("") { it } }
            .forEach(::println)
        
        return 1
    }
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day13_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 17)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 1)
        }
    }
    
    readInput("Day13").prepareInput().let { input ->
        part1(input).let {
            check(it == 671)
            println("Output Part 1: $it")
        }
        part2(input).let {
            check(it == 1)
            println("Output Part 2: $it")
        }
    }
}