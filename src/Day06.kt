fun main() {
    class Fish(val startDay: Int, val amtNewFish: Long, startAsReady: Boolean = false) {
        private var daysUntilReady = 2
        
        init {
            if (startAsReady) daysUntilReady = 0
        }
        
        val ready: Boolean
            get() = daysUntilReady <= 0
        
        fun tick() { daysUntilReady-- }
    }
    
    fun List<String>.prepareInput(): List<Fish> = first()
        .split(",")
        .map(String::toInt)
        .groupingBy { it }
        .eachCount()
        .map { (startDay, amt) -> Fish(startDay, amt.toLong(), true) }
    
    fun simulate(days: Int, fish: List<Fish>): Long = buildList {
        addAll(fish)
        (1 until days).forEach { day ->
            val dayMod7 = day.mod(7)
            val amtNewFish = sumOf { fish: Fish ->
                if (fish.ready && dayMod7 == fish.startDay) fish.amtNewFish else 0
            }
    
            forEach(Fish::tick)
            if (amtNewFish != 0L) add(Fish((dayMod7 + 2).mod(7), amtNewFish))
        }
    }.sumOf(Fish::amtNewFish)
    
    fun part1(fish: List<Fish>): Long = simulate(80, fish)
    
    fun part2(fish: List<Fish>): Long = simulate(256, fish)
    
    val testInput = readInput("Day06_test").prepareInput()
    part1(testInput).let {
        println("testPart1: $it")
        check(it == 5934L)
    }
    part2(testInput).let {
        println("testPart2: $it")
        check(it == 26984457539)
    }
    
    val input = readInput("Day06").prepareInput()
    part1(input).let {
        check(it == 345793L)
        println(it)
    }
    part2(input).let {
        check(it == 1572643095893)
        println(it)
    }
}