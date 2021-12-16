fun main() {
    abstract class Packet(val version: Int, val typeId: Int) {
        abstract val versionSum: Int
        abstract fun eval(): Long
    }
    
    class LiteralPacket(version: Int, typeId: Int, val value: Long): Packet(version, typeId) {
        override val versionSum: Int
            get() = version
    
        override fun eval(): Long = value
    }
    
    class OperatorPacket(version: Int, typeId: Int, val subPackets: List<Packet>): Packet(version, typeId) {
        override val versionSum: Int
            get() = version + subPackets.sumOf(Packet::versionSum)
    
        override fun eval(): Long = when (typeId) {
            0 -> subPackets.sumOf(Packet::eval)
            1 -> subPackets.productOf(Packet::eval)
            2 -> subPackets.minOf(Packet::eval)
            3 -> subPackets.maxOf(Packet::eval)
            5 -> if (subPackets.first().eval() > subPackets.last().eval()) 1 else 0
            6 -> if (subPackets.first().eval() < subPackets.last().eval()) 1 else 0
            7 -> if (subPackets.first().eval() == subPackets.last().eval()) 1 else 0
            else -> error("No operation defined for typeId $typeId")
        }
    }
    
    class Parser(text: String) {
        private val iterator = IterList(text.map(Char::digitToInt))
        fun parse(): Packet = parsePacket()
        
        private fun parsePacket(): Packet {
            val version = parseVersion()
            val typeId = parseTypeId()
            
            return if (typeId == 4) parseLiteralPacket(version, typeId)
            else parseOperatorPacket(version, typeId)
        }
        
        private fun parseOperatorPacket(version: Int, typeId: Int): OperatorPacket {
//            val lengthTypeId = parseTypeLengthId()
    
            val subPackets: List<Packet> = buildList {
                when (val lengthTypeId = parseTypeLengthId()) {
                    0 -> {
                        val totalLengthInBits = parseNumber(15)
                        val startPosition = iterator.position
                        while (iterator.position - startPosition < totalLengthInBits) add(parsePacket())
                    }
                    1 -> {
                        val numberOfSubPackets = parseNumber(11)
                        for (i in 1.. numberOfSubPackets) add(parsePacket())
                    }
                    else -> error("lengthTypeId cannot be anything other than 0 or 1, got $lengthTypeId")
                }
            }
            
//            val subPackets: List<Packet> = when (lengthTypeId) {
//                0 -> {
//                    val totalLengthInBits = parseNumber(15)
//                    val startPosition = iterator.position
//
//                    buildList {
//                        while (iterator.position - startPosition < totalLengthInBits) add(parsePacket())
//                    }
//                }
//                1 -> {
//                    val numberOfSubPackets = parseNumber(11)
//
//                    buildList {
//                        for (i in 1.. numberOfSubPackets) add(parsePacket())
//                    }
//                }
//                else -> error("lengthTypeId cannot be anything other than 0 or 1, got $lengthTypeId")
//            }
            
            return OperatorPacket(version, typeId, subPackets)
        }
        
        private fun parseLiteralPacket(version: Int, typeId: Int): LiteralPacket {
            val value = buildList {
                while (iterator.hasNext && iterator.peek!! == 1) add(parseLiteralGroup())
                add(parseLiteralGroup())
            }.joinToString("")
                .toLong(2)
            
            return LiteralPacket(version, typeId, value)
        }
        
        private fun parseLiteralGroup(): String {
            iterator.next
            return parseBinaryNumber(4)
        }
        
        private fun parseVersion(): Int = parseNumber(3).toInt()
        
        private fun parseTypeId(): Int = parseNumber(3).toInt()
        
        private fun parseTypeLengthId(): Int = parseNumber(1).toInt()
        
        private fun parseBinaryNumber(length: Int): String = buildString {
            repeat(length) { append(iterator.next) }
        }
        
        private fun parseNumber(length: Int): Long = parseBinaryNumber(length).toLong(2)
    }
    
    fun String.hexToBinary(): String = map { c ->
        c.digitToInt(16).toString(2).padStart(4, '0')
    }.joinToString("")
    
    fun part1(packet: Packet): Int = packet.versionSum
    
    fun part2(packet: Packet): Long = packet.eval()
    
    fun List<String>.prepareInput(): Packet = Parser(first().hexToBinary()).parse()
    
    part1(listOf("A0016C880162017C3686B18A3D4780").prepareInput()).let {
        println("Test Part 1: $it")
        check(it == 31)
    }
    part2(listOf("04005AC33890").prepareInput()).let {
        println("Test Part 2: $it\n")
        check(it == 54L)
    }
    
    readInput("Day16").prepareInput().let { input ->
        part1(input).let {
            check(it == 949)
            println("Output Part 1: $it")
        }
        part2(input).let {
            check(it == 1114600142730)
            println("Output Part 2: $it")
        }
    }
}