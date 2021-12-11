fun main() {
    data class Octopus(private var startValue: Int, val rowIdx: Int, val colIdx: Int) {
        val value: Int
            get() = startValue
        
        var hasFlashedThisStep = false
            private set
        
        val needsToFlash: Boolean
            get() = value >= 9 && !hasFlashedThisStep
        
        fun step() { startValue++ }
    
        fun afterStep() {
            hasFlashedThisStep = false
            startValue = if (value > 9) 0 else value
        }
        
        fun flash(): Boolean = if (needsToFlash) {
            hasFlashedThisStep = true
            true
        } else false
    
        override fun toString(): String = "Octopus($value)"
    }
    
    fun List<List<Octopus>>.print() = map { it.map { octopus -> octopus.value } }
        .forEach { println(it) }
        .also { println() }
    
    fun part1(octopi: List<List<Octopus>>): Int {
        var flashCnt = 0
        
        repeat(100) { _ ->
            while (octopi.anyElement(Octopus::needsToFlash)) {
                val octopiToFlash = octopi.flatMap { it.filter(Octopus::needsToFlash) }
                flashCnt += octopiToFlash.size
                
                octopiToFlash.forEach { octopus ->
                    octopus.flash()
                    octopi.surroundingValuesOf(octopus.rowIdx, octopus.colIdx)
                        .forEach(Octopus::step)
                }
            }
            
            octopi.forEachElement(Octopus::step)
            octopi.forEachElement(Octopus::afterStep)
        }
        
        return flashCnt
    }
    
    fun part2(list: List<List<Octopus>>): Int = 1
    
    fun List<String>.prepareInput(): List<List<Octopus>> = mapIndexed { rowIdx, line ->
        line.mapIndexed { colIdx, c ->  Octopus(c.digitToInt(), rowIdx, colIdx) }
    }
    
    readInput("Day11_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 1656)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 1)
        }
    }
    
    readInput("Day11").prepareInput().let { input ->
        part1(input).let {
            //check(it == 1)
            println("Output Part 1: $it")
        }
        part2(input).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}