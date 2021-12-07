fun main() {
    fun part1(list: List<String>): Int = 1
    
    fun part2(list: List<String>): Int = 1
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day08_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 1)
        }
        part2(testInput).let {
            println("Test Part 2: $it")
            check(it == 1)
        }
    }
    
    readInput("Day08").prepareInput().let { input ->
        part1(input).let {
            //check(it == 1)
            println(it)
        }
        part2(input).let {
            //check(it == 1)
            println(it)
        }
    }
}