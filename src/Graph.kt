import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.experimental.ExperimentalTypeInference

class Graph<T> {
    val adjacencyMap: HashMap<T, HashSet<T>> = HashMap()
    
    fun addEdge(sourceVertex: T, destinationVertex: T) {
        adjacencyMap.computeIfAbsent(sourceVertex) { HashSet() }
            .add(destinationVertex)
        
        adjacencyMap.computeIfAbsent(destinationVertex) { HashSet() }
            .add(sourceVertex)
    }
    
    fun removeVertex(vertex: T) {
        require(vertex in adjacencyMap.keys) { "Vertex $vertex is not part of this graph" }
        
        adjacencyMap.remove(vertex)!!
            .forEach { destination -> adjacencyMap[destination]!!.remove(vertex) }
    }
    
    fun removeAll(vertices: Iterable<T>) = vertices.forEach { removeVertex(it) }
    
    fun edgeCount(vertex: T): Int {
        require(vertex in adjacencyMap.keys) { "Vertex $vertex is not part of this graph" }
        
        return adjacencyMap[vertex]!!.size
    }
    
    fun depthFirstTraversal(startVertex: T): List<T> {
        require(startVertex in adjacencyMap.keys) { "StartVertex $startVertex is not part of this graph" }
    
        // Mark all the vertices / nodes as not visited.
        val visitedMap = adjacencyMap.keys.associateWith { false }.toMutableMap()
    
        // Create a stack for DFS. Both ArrayDeque and LinkedList implement Deque.
        val stack: Deque<T> = ArrayDeque()
    
        // Initial step -> add the startNode to the stack.
        stack.push(startVertex)
    
        // Store the sequence in which nodes are visited, for return value.
        val traversalList = mutableListOf<T>()
        
        while (stack.isNotEmpty()) {
            val currentVertex = stack.pop()
            
            if (!visitedMap[currentVertex]!!) {
                traversalList.add(currentVertex)
                visitedMap[currentVertex] = true
                
                adjacencyMap[currentVertex]?.forEach(stack::push)
            }
        }
        
        return traversalList
    }
    
    override fun toString(): String = buildString {
        for ((key, value) in adjacencyMap)
            append("$key -> ${value.joinToString(", ", "[", "]\n")}")
    }
}

@ExperimentalStdlibApi
@ExperimentalContracts
inline fun <T> buildGraph(builderAction: Graph<T>.() -> Unit): Graph<T> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    val graph = Graph<T>()
    graph.builderAction()
    return graph
}