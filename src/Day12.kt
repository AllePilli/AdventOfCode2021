import java.util.*
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@ExperimentalStdlibApi
//TODO use path counter instead of keeping whole list in memory. because only the size of the paths list matters
fun main() {
    data class Cave(val name: String) {
        val isSmallRoom by lazy { name.first().isLowerCase() }
    }
    
    fun Graph<Cave>.allPaths(source: Cave, destination: Cave): List<List<Cave>> {
        val paths = mutableListOf<List<Cave>>()
        val visited = mutableMapOf<String, Boolean>()
        
        fun allPaths(source: Cave, destination: Cave, localPathsStack: Stack<Cave>) {
            if (source == destination) paths.add(localPathsStack.toList())
            else {
                visited[source.name] = true
                
                for (cave in adjacencyMap[source]!!)
                    if (!cave.isSmallRoom || !visited.computeIfAbsent(cave.name) { false })
                        localPathsStack.pushPop(cave) {
                            allPaths(cave, destination, localPathsStack)
                        }
                
                visited[source.name] = false
            }
        }
        
        allPaths(source, destination, stackOf(source))
        return paths
    }
    
    fun Graph<Cave>.extendedPaths(source: Cave, destination: Cave): List<List<Cave>> {
        val paths = mutableListOf<List<Cave>>()
        val visited = mutableMapOf<String, Int>()
        
        fun allPaths(source: Cave, destination: Cave, localPathsStack: Stack<Cave>) {
            if (source == destination) paths.add(localPathsStack.toList())
            else {
                if (source.isSmallRoom) visited[source.name] = visited.computeIfAbsent(source.name) { 0 } + 1
                
                for (cave in adjacencyMap[source]!!.filterNot { it.name == "start" }) {
                    val timesVisited by lazy { visited.computeIfAbsent(cave.name) { 0 } }
                    if (cave.name == "end" || !cave.isSmallRoom || timesVisited == 0
                        || (timesVisited == 1 && visited.values.none { it == 2 })) {
                        
                        localPathsStack.pushPop(cave) {
                            allPaths(cave, destination, localPathsStack)
                        }
                    }
                }
                
                if (source.isSmallRoom) visited[source.name] = visited[source.name]!! - 1
            }
        }
        
        allPaths(source, destination, stackOf(source))
        
        return paths.filter { path ->
            val frequencies = path.filter(Cave::isSmallRoom)
                .map(Cave::name)
                .groupingBy { it }
                .eachCount()
                .values
            
            frequencies.all { it <= 2 } && frequencies.count { it == 2 } <= 1
        }
    }
    
    fun part1(list: List<List<String>>): Int = buildGraph<Cave> {
        list.forEach { (v1, v2) -> addEdge(Cave(v1), Cave(v2)) }

        val unvisitableCaves = adjacencyMap
            .filterValues { destinations -> destinations.singleOrNull()?.isSmallRoom ?: false }
            .keys

        removeAll(unvisitableCaves)
    }.allPaths(Cave("start"), Cave("end")).size
    
    fun part2(list: List<List<String>>): Int = buildGraph<Cave> {
        list.forEach { (v1, v2) -> addEdge(Cave(v1), Cave(v2)) }
    }.extendedPaths(Cave("start"), Cave("end")).size
    
    fun List<String>.prepareInput(): List<List<String>> = map { it.split("-") }
    
    readInput("Day12_test").prepareInput().let { testInput ->
        part1(testInput).let {
            println("Test Part 1: $it")
            check(it == 10)
        }
        part2(testInput).let {
            println("Test Part 2: $it\n")
            check(it == 36)
        }
    }
    
    readInput("Day12").prepareInput().let { input ->
        part1(input).let {
            check(it == 3497)
            println("Output Part 1: $it")
        }
        part2(input).let {
            check(it == 93686)
            println("Output Part 2: $it")
        }
    }
}