fun main() {
    fun part1(list: List<String>): Int = 1
    
    fun part2(list: List<String>): Int = 1
    
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)
    
    val input = readInput("Day05")
    part1(input).let {
        check(it == 1)
        println(it)
    }
    part2(input).let {
        check(it == 1)
        println(it)
    }
}