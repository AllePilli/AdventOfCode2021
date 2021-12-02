fun main() {
    val commandRgx = """(\w+) (\d+)""".toRegex()
    
    fun part1(input: List<Pair<String, Long>>): Long {
        var horizontal = 0L
        var depth = 0L
        
        input.forEach { (command, value) ->
            when (command) {
                "forward" -> horizontal += value
                "up" -> depth -= value
                "down" -> depth += value
            }
        }
        
        return horizontal * depth
    }
    
    fun part2(input: List<Pair<String, Long>>): Long {
        var horizontal = 0L
        var aim = 0L
        var depth = 0L
        
        input.forEach { (command, value) ->
            when (command) {
                "forward" -> {
                    horizontal += value
                    depth += value * aim
                }
                "up" -> aim -= value
                "down" -> aim += value
            }
        }
        
        return horizontal * depth
    }
    
    fun preprocessInput(input: List<String>): List<Pair<String, Long>> = input.map {
        commandRgx.find(it)!!.destructured.let { (command, value) ->
            command to value.toLong()
        }
    }
    
    val testInput = preprocessInput(readInput("Day02_test"))
    check(part1(testInput) == 150L)
    check(part2(testInput) == 900L)
    
    val input = preprocessInput(readInput("Day02"))
    part1(input).let { result ->
        check(result == 1694130L)
        println(result)
    }
    part2(input).let { result ->
        check(result == 1698850445L)
        println(result)
    }
}