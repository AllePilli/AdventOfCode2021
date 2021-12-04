fun main() {
    fun part1(list: List<List<Int>>): Int {
        val half = list.size / 2
    
        val gammaRate = list.first()
            .indices
            .map { idx -> list.sumOf { it[idx] } }
            .map { columnSum -> if (columnSum >= half) 1 else 0 }
            .joinToString(separator = "")
    
        val epsilonRate = gammaRate.map { if (it == '1') '0' else '1' }.joinToString(separator = "")
        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }
    
    fun part2(list: List<List<Int>>): Int {
        var oxygenRating = list
    
        for (idx in list.first().indices) {
            val oneFrequency = oxygenRating.count { it[idx] == 1 }
            val mostCommonBit = if (oneFrequency >= oxygenRating.size - oneFrequency) 1 else 0
    
            oxygenRating = oxygenRating.filter { number -> number[idx] == mostCommonBit }
            if (oxygenRating.size == 1) break
        }
    
        var co2Rating = list

        for (idx in list.first().indices) {
            val zeroFrequency = co2Rating.count { it[idx] == 0 }
            val leastCommonBit = if (zeroFrequency <= co2Rating.size - zeroFrequency) 0 else 1

            co2Rating = co2Rating.filter { number -> number[idx] == leastCommonBit }
            if (co2Rating.size == 1) break
        }
        
        return oxygenRating.single().joinToString(separator = "").toInt(2) *
                co2Rating.single().joinToString(separator = "").toInt(2)
    }
    
    val testInput = readInput("Day03_test").map { it.map(Char::digitToInt) }
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)
    
    val input = readInput("Day03").map { it.map(Char::digitToInt) }
    part1(input).let {
        check(it == 3009600)
        println(it)
    }
    part2(input).let {
        check(it == 6940518)
        println(it)
    }
}