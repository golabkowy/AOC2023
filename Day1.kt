import utils.readFileLineByLineUsingForEachLine

fun main() {
    val lines = readFileLineByLineUsingForEachLine("./inputFiles/day1.txt")

    val pattern = mapOf(
        Pair("one", "1"),
        Pair("two", "2"),
        Pair("three", "3"),
        Pair("four", "4"),
        Pair("five", "5"),
        Pair("six", "6"),
        Pair("seven", "7"),
        Pair("eight", "8"),
        Pair("nine", "9"),
    )

    val numbers: MutableList<MutableList<Pair<String, Int>>> = mutableListOf()

    lines.forEachIndexed { i, line ->
        numbers.add(mutableListOf())

        pattern.forEach { (key) ->
            var index = 0
            while (index != -1) {
                index = line.indexOf(string = key, startIndex = index)
                if (index != -1) {
                    numbers[i].add(Pair(pattern[key]!!, index))
                    index = index.inc()
                }
            }

        }

        line.forEachIndexed { j, c ->
            if (c.isDigit()) {
                numbers[i].add(Pair(c.toString(), j))
            }
        }
    }

    numbers.forEach { it.sortBy { it.second } }
    val results: List<String> = numbers.map { it.first().first + it.last().first }
    println(results.map { it.toInt() }.sum())
}