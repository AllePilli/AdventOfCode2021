fun main() {
    fun simulate(template: String, rules: Map<String, String>, times: Int): String = (1..times)
        .fold(template) { polymer, _ ->
            println(polymer)
            buildString {
                append(polymer.first())
                polymer.zipWithNext()
                    .forEach { (first, second) -> append(rules["$first$second"]!!, second) }
            }
        }
    
    fun specialSimulate(template: String, mappings: Map<String, String>, times: Int): String {
        var polymer = template
        val internalMappings = mappings.toMutableMap()
        val sortedKeys = mappings.keys.sortedBy(String::length).toMutableList()
        val sortedKeysReversed = (sortedKeys as List<String>).asReversed()
        
        repeat(times) {
            polymer = buildString {
                var i = 0
                for (key in sortedKeysReversed) {
                    if (i >= polymer.length) break
                    else if (i + key.length >= polymer.length) continue
                    
                    if (polymer.substring(i, i + key.length) == key) {
                        append(internalMappings[key])
                        i += key.length
                    }
                }
                
                internalMappings[polymer] = this.toString()
                sortedKeys.add(polymer)
            }
        }
        
        return polymer
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
        val mappings = list.drop(2)
            .associate { line ->
                line.split(" -> ").let { (first, second) ->
                    first to "${first.first()}$second${first.last()}"
                }
            }
//        val rules = list.drop(2)
//            .associate { line ->
//                line.split(" -> ").let { (first, second) -> first to second }
//            }
    
        return specialSimulate(list.first(), mappings, 40)//simulate(list.first(), rules, 40)
            .groupingBy { it }
            .eachCountLong()
            .run { maxOf { it.value } - minOf { it.value } }
    }
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day14_test").prepareInput().let { testInput ->
//        part1(testInput).let {
//            println("Test Part 1: $it")
//            check(it == 1588)
//        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 2188189693529)
        }
    }
    
//    readInput("Day14").prepareInput().let { input ->
//        part1(input).let {
//            check(it == 3342)
//            println("Output Part 1: $it")
//        }
//        part2(input).let {
//            //check(it == 1)
//            println("Output Part 2: $it")
//        }
//    }
}