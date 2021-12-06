fun main() {
    fun part1(list: List<String>): Int {
        var fish = list.first()
            .split(",")
            .map(String::toInt)
            .toMutableList()
    
        var amtNewFish: Int
        
        repeat(80) { _ ->
            amtNewFish = fish.count { it == 0 }
            
            fish = fish.map { if (it == 0) 6 else it - 1 }
                .toMutableList()
                .also { it.addAll(List(amtNewFish) { 8 }) }
        }
        
        return fish.size
    }
    
    fun part2(list: List<String>): Int = 1
    
    val testInput = readInput("Day06_test")
    part1(testInput).let {
        println("testPart1: $it")
        check(it == 5934)
    }
    check(part2(testInput) == 1)
    
    val input = readInput("Day06")
    part1(input).let {
        check(it == 345793)
        println(it)
    }
    part2(input).let {
        //        check(it == 1)
        println(it)
    }
}