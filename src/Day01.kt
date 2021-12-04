fun main() {
    fun part1(input: List<String>): Int = input.map(String::toInt)
        .zipWithNext { a, b -> b > a }
        .count { it }

    fun part2(input: List<String>): Int = input.map(String::toInt)
        .windowed(3) { window -> window.sum() }
        .zipWithNext { a, b -> b > a }
        .count { it }
    
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
