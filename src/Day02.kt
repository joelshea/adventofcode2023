//import kotlin.text.Regex

const val MAX_RED   = 12
const val MAX_GREEN = 13
const val MAX_BLUE  = 14

val starterMap = mapOf ("green" to 0, "red" to 0, "blue" to 0 )

fun main() {
    fun part1(input: List<String>): Int {
        val indexes = mutableListOf<Int>()
        for (line in input) {
            val parts = line.split(":")
            val gameIndex = parts.first().substring(5).toInt()
            val gamePulls = parts.last().split(";").map{
                it.split(",").associate { cube ->
                    val value = cube.substring(cube.indexOf(" ") + 1, cube.lastIndexOf(" ")).toInt()
                    val color = cube.substring(cube.lastIndexOf(" ") + 1)
                    value to color
                }
            }

            gamePulls.filter {
                val count = it.filter {
                    val (value, color) = it
                    when (color) {
                        "red"   -> value <= MAX_RED
                        "green" -> value <= MAX_GREEN
                        "blue"  -> value <= MAX_BLUE
                        else -> false
                    }
                }.count()
                count == it.count()
            }.run { if (count() == gamePulls.count()) indexes.add(gameIndex) }
        }

        return indexes.sum()
    }

    fun part2(input: List<String>): Int {
        val powers = mutableListOf<Int>()
        for (line in input) {
            val parts = line.split(":")
            val gamePulls = parts.last().split(";").map{
                it.split(",").associate { cube ->
                    val value = cube.substring(cube.indexOf(" ") + 1, cube.lastIndexOf(" ")).toInt()
                    val color = cube.substring(cube.lastIndexOf(" ") + 1)
                    color to value
                }
            }

            val highestCubes = gamePulls.fold(starterMap) { key, acc ->
                val getValue = { text: String -> if (key[text]!! > acc.getOrDefault(text, 0)) key[text] else acc.getOrDefault(text, 0) }
                mapOf(
                    "green" to getValue("green")!!,
                    "blue" to getValue("blue")!!,
                    "red" to getValue("red")!!
                )
            }

            powers.add(highestCubes["green"]!! * highestCubes["blue"]!! * highestCubes["red"]!!)
        }

        return powers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_test")
    val testInput2 = readInput("Day02_test")
    check(part1(testInput1) == 8)
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

