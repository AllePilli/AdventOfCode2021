fun main() {
    fun part1(list: List<String>): Int = 1
    
    fun part2(list: List<String>): Int = 1
    
    readInput("Day0X_test").let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 1)
        }
        part2(testInput).let {
            println("Test Part 2: $it")
            check(it == 1)
        }
    }
    
    readInput("Day0X").let { input ->
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