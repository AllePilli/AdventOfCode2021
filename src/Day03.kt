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
    
    fun part2(input: List<String>): Int {
        val list = input.map { it.map(Char::digitToInt) }
        val half = list.size / 2
        
        val gammaRate = list.first()
            .indices
            .map { idx ->
                val oneCnt = list.count { it[idx] == 1 }
                val zeroCnt = list.count { it[idx] == 0 }
                if (oneCnt >= zeroCnt) 1
                else 0
            }
//            .map { idx -> list.sumOf { it[idx] } }
//            .map { columnSum -> if (columnSum >= half) 1 else 0 }
        
        var oxygenRating = list
    
        for (idx in list.first().indices) {
            val oneFrequency = oxygenRating.count { it[idx] == 1 }
            val zeroFrequency = oxygenRating.count { it[idx] == 0 }
            val mostCommonBit = if (oneFrequency >= zeroFrequency) 1 else 0
    
            oxygenRating = oxygenRating.filter { number -> number[idx] == mostCommonBit }
            if (oxygenRating.size == 1) break
        }
        
//        for ((idx, bit) in gammaRate.withIndex()) {
//            oxygenRating = oxygenRating.filter { number -> number[idx] == bit }
//            if (oxygenRating.size == 1) break
//        }
    
        val modifiedEpsilonRate = list.first()
            .indices
            .map { idx ->
                val oneCnt = list.count { it[idx] == 1 }
                val zeroCnt = list.count { it[idx] == 0 }
                if (zeroCnt <= oneCnt) 0
                else 1
            }
//            .map { idx -> list.sumOf { it[idx] } }
//            .map { columnSum -> if (columnSum >= half) 0 else 1 }
    
        var co2Rating = list
        
        for (idx in list.first().indices) {
            val oneFrequency = co2Rating.count { it[idx] == 1 }
            val zeroFrequency = co2Rating.count { it[idx] == 0 }
            val leastCommonBit = if (zeroFrequency <= oneFrequency) 0 else 1
            
            co2Rating = co2Rating.filter { number -> number[idx] == leastCommonBit }
            if (co2Rating.size == 1) break
        }
        
//        for ((idx, bit) in modifiedEpsilonRate.withIndex()) {
//            co2Rating = co2Rating.filter { number -> number[idx] == bit }
//            if (co2Rating.size == 1) break
//        }
        
        return oxygenRating.single().joinToString(separator = "").toInt(2) *
                co2Rating.single().joinToString(separator = "").toInt(2)
    }
    
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)
    
    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}