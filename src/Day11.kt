fun main() {
    data class Octopus(private val startValue: Int, val rowIdx: Int, val colIdx: Int) {
        var value = startValue
            private set
        
        var hasFlashedThisStep = false
            private set
        
        val needsToFlash: Boolean
            get() = value >= 9 && !hasFlashedThisStep
        
        fun step() { value++ }
    
        fun afterStep() {
            hasFlashedThisStep = false
            value = if (value > 9) 0 else value
        }
        
        fun flash(): Boolean = if (needsToFlash) {
            hasFlashedThisStep = true
            true
        } else false
        
        fun reset() { value = startValue }
    }
    
    fun part1(octopi: List<List<Octopus>>): Int = with(octopi) {
        var flashCnt = 0

        repeat(100) { _ ->
            while (anyElement(Octopus::needsToFlash)) {
                val octopiToFlash = flatMap { it.filter(Octopus::needsToFlash) }
                flashCnt += octopiToFlash.size
        
                octopiToFlash.forEach { octopus ->
                    octopus.flash()
                    surroundingValuesOf(octopus.rowIdx, octopus.colIdx).forEach(Octopus::step)
                }
            }
    
            forEachElement(Octopus::step)
            forEachElement(Octopus::afterStep)
        }

        forEachElement(Octopus::reset)

        flashCnt
    }
    
    fun part2(octopi: List<List<Octopus>>): Int = with(octopi) {
        var step = 0
        while (!allElement { it.value == 0 }) {
            step++
            while (anyElement(Octopus::needsToFlash)) flatMap { it.filter(Octopus::needsToFlash) }
                .forEach { octopus ->
                    octopus.flash()
                    surroundingValuesOf(octopus.rowIdx, octopus.colIdx).forEach(Octopus::step)
                }
    
            forEachElement(Octopus::step)
            forEachElement(Octopus::afterStep)
        }
        
        step
    }
    
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
            check(it == 195)
        }
    }
    
    readInput("Day11").prepareInput().let { input ->
        part1(input).let {
            check(it == 1642)
            println("Output Part 1: $it")
        }
        part2(input).let {
            check(it == 320)
            println("Output Part 2: $it")
        }
    }
}