fun main() {
    fun String.sorted() = toCharArray().sorted().joinToString(separator = "")
    
    fun String.getNumber(easyDigitKeys: Set<String>): Int = if (length == 6) {
        val number1 = easyDigitKeys.find { it.length == 2 }!!
        val number4 = easyDigitKeys.find { it.length == 4 }!!
    
        if (!number1.all { c -> c in this }) 6
        else if (number4.all { c -> c in this }) 9
        else 0
    } else if (length == 5) {
        val number7 = easyDigitKeys.find { it.length == 3 }!!
        val number4 = easyDigitKeys.find { it.length == 4 }!!
    
        if (number4.count { c -> c in this } == 2) 2
        else if (number7.all { c -> c in this }) 3
        else 5
    } else error("cannot have numberSegment of size not equal to 5 or 6")
    
    val amtSegmentsForEasyDigits = mapOf(2 to 1, 4 to 4, 3 to 7, 7 to 8)
    
    fun part1(list: List<String>): Int = list.sumOf { line ->
        line.split("|")
            .last()
            .trim()
            .split("""\s+""".toRegex())
            .filter { outputNumber -> outputNumber.length in amtSegmentsForEasyDigits.keys }
            .size
    }
    
    fun part2(list: List<String>): Int = list.sumOf { line ->
        line.split("|")
            .let { (input, output) ->
                input.trim().split("""\s+""".toRegex()) to output.trim().split("""\s+""".toRegex())
            }
            .let { (input, output) ->
                val easyDigits = amtSegmentsForEasyDigits.map { (amtSegments, number) ->
                    input.find { numberSegments -> numberSegments.length == amtSegments }!! to number
                }.toMap()
                
                val difficultDigits = input
                    .filterNot { it in easyDigits.keys }
                    .associateWith { numberSegments -> numberSegments.getNumber(easyDigits.keys) }

                val digitMap = (easyDigits.keys + difficultDigits.keys).associate { key ->
                    key.sorted() to (easyDigits[key] ?: difficultDigits[key]!!)
                }
                
                output.map { it.trim() }
                    .joinToString(separator = "") { segmentedNumber ->
                        digitMap[segmentedNumber.sorted()]!!.toString()
                    }.toInt()
            }
    }
    
    fun List<String>.prepareInput(): List<String> = this
    
    readInput("Day08_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 26)
        }
        part2(testInput).let {
            println("Test Part 2: $it")
            check(it == 61229)
        }
    }
    
    readInput("Day08").prepareInput().let { input ->
        part1(input).let {
            check(it == 284)
            println(it)
        }
        part2(input).let {
            check(it == 973499)
            println(it)
        }
    }
}