import java.lang.Exception
import kotlin.text.Regex

fun main() {
    val ABOVE = -1
    val BELOW = 1
    val notSymbol = ".0123456789"

    fun part1(input: List<String>): Int {
        val partNumbers = mutableListOf<Int>()
        for (line in input.withIndex()) {
            try {
                val matches = Regex("\\d+").findAll(line.value)
                for (match in matches) {
                    val adjacentString = { direction:Int ->
                        input[line.index+direction].substring((match.range.first-1).coerceAtLeast(0),
                            (match.range.last+2).coerceAtMost(line.value.lastIndex))
                    }

                    if (line.index > 0) {
                        if (adjacentString(ABOVE).doesNotContain(notSymbol)) {
                            partNumbers.add(match.value.toInt())
                        }
                    }
                    if (line.value.substring(
                            (match.range.first-1).coerceAtLeast(0),
                            match.range.first)
                            .doesNotContain(notSymbol) ||
                        line.value.substring(
                            (match.range.last+1).coerceAtMost(line.value.lastIndex),
                            (match.range.last+2).coerceAtMost(line.value.lastIndex))
                            .doesNotContain(notSymbol)) {
                        partNumbers.add(match.value.toInt())
                    }
                    if (line.index+1 != input.count()) {
                        if (adjacentString(BELOW).doesNotContain(notSymbol)) {
                            partNumbers.add(match.value.toInt())
                        }
                    }
                }
            } catch (e: Exception) {
                println("Failed on input line: ${line.index}")
                throw e
            }

        }

        return partNumbers.sum()
    }

    fun part2(input: List<String>): Int {
        val gearRatios = mutableSetOf<Int>()
        val gearsForLines = mutableMapOf<Int, Sequence<MatchResult>>()
        val partNumbersForLines = mutableMapOf<Int, Sequence<MatchResult>>()
        for (line in input.withIndex()) {
            gearsForLines[line.index] = Regex("\\*").findAll(line.value)
            partNumbersForLines[line.index] = Regex("\\d+").findAll(line.value)
        }

        for (gearsForLine in gearsForLines) {
            val gears = gearsForLine.value
            for (gear in gears) {
                val trueParts = mutableListOf<Int>()
                println("${gearsForLine.key}, ${gear.range}")
                if (gearsForLine.key > 0) {
                    for (partNumber in partNumbersForLines[gearsForLine.key-1]!!) {
                        if (partNumber.range.intersect(gear.range.plus(gear.range.first-1).plus(gear.range.last+1).toSet()).isNotEmpty()) {
                            trueParts.add(partNumber.value.toInt())
                        }
                    }
                }
                for (partNumber in partNumbersForLines[gearsForLine.key]!!) {
                    if (partNumber.range.last+1 == gear.range.first || partNumber.range.first == gear.range.last+1) {
                        trueParts.add(partNumber.value.toInt())
                    }
                }
                if (gearsForLine.key+1 != input.count()) {
                    for (partNumber in partNumbersForLines[gearsForLine.key+1]!!) {
                        if (partNumber.range.intersect(gear.range.plus(gear.range.first-1).plus(gear.range.last+1).toSet()).isNotEmpty()) {
                            trueParts.add(partNumber.value.toInt())
                        }
                    }
                }

                if (trueParts.count() == 2) {
                    println(trueParts)
                    gearRatios.add(trueParts.first() * trueParts.last())
                }
            }
        }

        println(gearRatios)
        return gearRatios.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day03_test")
    val testInput2 = readInput("Day03_test")
    checkAndPrint(4361, part1(testInput1))
    checkAndPrint(467835, part2(testInput2))

    println("-- Run the real test")
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}