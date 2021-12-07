import kotlin.math.abs

fun main() {
    fun part1(positions: List<Int>): Int {
        fun Int.fuelUsage(): Int = positions.sumOf { secondPosition -> abs(this - secondPosition) }
        
        val minFuelPosition = positions.minByOrNull(Int::fuelUsage) ?: error("No minimum fuel position found")
        return minFuelPosition.fuelUsage()
    }
    
    fun part2(positions: List<Int>): Int {
        fun Int.fuelUsage(): Int = positions.sumOf { secondPosition ->
            val diff = abs(this - secondPosition)
            diff * (diff + 1) / 2
        }
    
        val minFuelPosition = with(positions) {
            (minOf { it }..maxOf { it }).minByOrNull(Int::fuelUsage) ?: error("No minimum fuel position found")
        }
        
        return minFuelPosition.fuelUsage()
    }
    
    val testInput = readInput("Day07_test").first().split(",").map(String::toInt)
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)
    
    val input = readInput("Day07").first().split(",").map(String::toInt)
    part1(input).let {
        check(it == 344735)
        println(it)
    }
    part2(input).let {
        check(it == 96798233)
        println(it)
    }
}