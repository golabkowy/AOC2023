import utils.readLineSeparatedSections

fun main() {
    val sections = readLineSeparatedSections("./inputFiles/day5.txt")

    val (seeds, maps) = sections.getSeedsAndMaps()
    val parsedSeeds = seeds.first().split(':')[1].extractDigits()

    val results = parsedSeeds.map { seed ->
        var deeperLevel = seed
        maps.forEach { map ->
            val foundEntry = map.drop(1).firstOrNull { entry ->
                val (destination, source, step) = entry.extractDigits()
                deeperLevel in source..source + step
            }

            foundEntry?.let {
                val (destination, source, _) = it.extractDigits()
                deeperLevel = destination + (deeperLevel - source)
            }
        }
        deeperLevel
    }
    println(results.min())
}

fun List<MutableList<String>>.getSeedsAndMaps(): Pair<List<String>, List<MutableList<String>>> {
    val seeds = first()
    val maps = drop(1)
    return Pair(seeds, maps)
}

fun String.extractDigits(): List<Long> = trim().split("\\s+".toRegex()).map { it.toLong() }
