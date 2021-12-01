fun main() {
    fun part1(input: List<String>): Int = 1
    
    fun part2(input: List<String>): Int = 1
    
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)
    
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}