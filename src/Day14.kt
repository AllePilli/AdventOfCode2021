fun main() {
    fun simulate(template: String, rules: Map<String, String>, times: Int): String = (1..times)
        .fold(template) { polymer, _ ->
            buildString {
                append(polymer.first())
                polymer.zipWithNext()
                    .forEach { (first, second) -> append(rules["$first$second"]!!, second) }
            }
        }
    
    fun part1(list: List<String>): Int {
        val rules = list.drop(2)
            .associate { line ->
                line.split(" -> ").let { (first, second) -> first to second }
            }
        
        return simulate(list.first(), rules, 10)
            .groupingBy { it }
            .eachCount()
            .run { maxOf { it.value } - minOf { it.value } }
    }
    
    fun part2(list: List<String>): Long {
        var start = list.first()
        val rules = list.drop(2)
            .associate { line ->
                line.split(" -> ").let { (first, second) -> first to second }
            }
    
        repeat(40) {
            start = buildString {
                append(start.first())
                start.zipWithNext()
                    .forEach { (first, second) -> append(rules["$first$second"]!!, second) }
            }
        }
    
        return start.groupingBy { it }.eachCountLong().run {
            maxOf { it.value } - minOf { it.value }
        }
    }
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day14_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 1588)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 2188189693529)
        }
    }
    
    readInput("Day14").prepareInput().let { input ->
        part1(input).let {
            check(it == 3342)
            println("Output Part 1: $it")
        }
        part2(input).let {
            //check(it == 1)
            println("Output Part 2: $it")
        }
    }
}