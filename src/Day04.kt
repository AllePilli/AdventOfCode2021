fun main() {
    class Board(private val numbers: List<List<Int>>) {
        private val marked: List<MutableList<Boolean>> = numbers.map { it.map { false }.toMutableList() }
        
        var lastNumber = -1
            private set
        
        var won: Boolean = false
            private set
        
        fun mark(number: Int): Unit = numbers.withIndex()
            .flatMap { (idx, row) ->
                row.withIndex()
                    .filter { (_, value) -> value == number }
                    .map { matches -> idx to matches.index }
            }
            .forEach { (y, x) -> marked[y][x] = true }
            .also { lastNumber = number }
        
        fun hasBingo(): Boolean {
            // horizontal
            marked.forEach { row ->
                if (row.all { it }) {
                    won = true
                    return true
                }
            }
            
            // vertical
            marked.forEachCol { col ->
                if (col.all { it }) {
                    won = true
                    return true
                }
            }
            
            return false
        }
    
        fun sumUnmarked(): Int = marked
            .flatMapIndexed { rowIdx, row ->
                row.mapIndexedNotNull { colIdx, b -> if (!b) rowIdx to colIdx else null }
            }
            .sumOf { (rowIdx, colIdx) -> numbers[rowIdx][colIdx] }
    }
    
    val whiteSpaceRgx = """\s+""".toRegex()
    
    fun part1(numbersToMark: List<Int>, boards: List<Board>): Int {
        numbersToMark.forEach { number ->
            boards.forEach { it.mark(number) }
            boards.find(Board::hasBingo)
                ?.let { return it.sumUnmarked() * number }
        }
        
        throw IllegalStateException("Should have a bingo")
    }
    
    fun part2(numbersToMark: List<Int>, boards: MutableList<Board>): Int {
        val bingoOrder = mutableListOf<Board>()
    
        for (number in numbersToMark) {
            val stillInTheGame = boards.filterNot(Board::won)
            
            stillInTheGame.forEach {
                it.mark(number)
                it.hasBingo()
            }
            
            bingoOrder.addAll(stillInTheGame.filter(Board::won))
            
            if (bingoOrder.size == boards.size) break
        }
    
        return bingoOrder.last().sumUnmarked() * bingoOrder.last().lastNumber
    }
    
    fun prepareInput(input: List<String>): Pair<List<Int>, List<Board>> {
        val numbersToMark = input.first()
            .split(",")
            .map(String::toInt)

        val boards = input.drop(1)
            .filterNot(String::isBlank)
            .chunked(5)
            .map { lines ->
                Board(
                    lines.map { line ->
                        line.split(whiteSpaceRgx)
                            .filterNot { it.isBlank() || it.isEmpty() }
                            .map(String::toInt)
                    }
                )
            }
        
        return numbersToMark to boards
    }
    
    val (testNumbersToMark, testBoards) = prepareInput(readInput("Day04_test"))
    check(part1(testNumbersToMark, testBoards) == 4512)
    check(part2(testNumbersToMark, testBoards.toMutableList()) == 1924)
    
    val (numbersToMark, boards) = prepareInput(readInput("Day04"))
    part1(numbersToMark, boards).let {
        check(it == 50008)
        println(it)
    }
    part2(numbersToMark, boards.toMutableList()).let {
        check(it == 17408)
        println(it)
    }
}
