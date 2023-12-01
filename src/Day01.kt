import kotlin.text.Regex

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(0) { key, acc -> "${acc.first { it.isDigit() }}${acc.last { it.isDigit() }}".toInt() + key }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val numbers = mutableMapOf<Int, String>()
            for (digit in digits) {
                val matches = Regex("${digit.key}").findAll(line)
                matches.forEach { numbers[it.range.first] = digits[it.value] ?: throw Exception() }
            }
            val sortedKeys = numbers.keys.sorted()
            val first = numbers[sortedKeys.first()]
            val last = numbers[sortedKeys.last()]
            sum+="$first$last".toInt()
        }

        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test")
    val testInput2 = readInput("Day01_test_part2")
    check(part1(testInput1) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

val digits = mapOf(
    "1" to "1",
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9",
    "0" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five"  to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
    "zero" to "0"
)