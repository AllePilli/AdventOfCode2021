fun main() {
    fun part1(input: List<String>): Int {
        val list = input.map { it.map(Char::digitToInt) }
        val half = list.size / 2
    
        val gammaRate = list.first()
            .indices
            .map { idx -> list.sumOf { it[idx] } }
            .map { columnSum -> if (columnSum >= half) 1 else 0 }
            .joinToString(separator = "")
    
        val epsilonRate = gammaRate.map { if (it == '1') '0' else '1' }.joinToString(separator = "")
        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }
    
    fun part2(input: List<String>): Int = 1
    
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 1)
    
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}