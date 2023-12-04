fun main() {
    val dayNumber = "XX"

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day${dayNumber}_test")
    val testInput2 = readInput("Day${dayNumber}_test")
    checkAndPrint(100, part1(testInput1))
    checkAndPrint(100, part2(testInput2))

    println("-- Run the real test")
    val input = readInput("Day$dayNumber")
    part1(input).println()
    part2(input).println()
}