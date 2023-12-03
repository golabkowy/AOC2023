import utils.readFileLineByLineUsingForEachLine

data class GameSet(val red: Int?, val green: Int?, val blue: Int?)
data class Game(val id: Int, val sets: List<GameSet>, var isValid: Boolean = true)

fun main() {
    val data = readFileLineByLineUsingForEachLine("./inputFiles/day2.txt")
    val games: MutableList<Game> = mutableListOf()
    data.forEach {
        val (gameID, sets) = it.split(':')
        val gameSets = parseGameSets(sets)

        val game = Game(id = gameID.split(' ')[1].toInt(), sets = gameSets)

        games.add(game)
    }

    // part 1
    val firstResult =
        games
            .markValidGames(12, 13, 14)
            .sumValidGameIds()
    println(firstResult)

    // part 2
    val secondResult = games.secondPart()
    println(secondResult)

}

fun parseGameSets(input: String): List<GameSet> {
    val sets = input.split(";")

    return sets.map { set ->
        val colors = set.trim().split(", ")

        val red = colors.find { it.endsWith("red") }?.split(" ")?.get(0)?.toIntOrNull()
        val green = colors.find { it.endsWith("green") }?.split(" ")?.get(0)?.toIntOrNull()
        val blue = colors.find { it.endsWith("blue") }?.split(" ")?.get(0)?.toIntOrNull()

        GameSet(red, green, blue)
    }
}

fun List<Game>.markValidGames(red: Int, green: Int, blue: Int): List<Game> {
    forEach { game ->
        game.isValid = game.sets.none { set ->
            (set.red != null && set.red > red) ||
                    (set.green != null && set.green > green) ||
                    (set.blue != null && set.blue > blue)
        }
    }
    return this
}

fun List<Game>.secondPart(): Int =
    sumOf { game ->
        val minRed = game.copy().sets.map { it.red }.filterNotNull().maxOrNull()
        val minGreen = game.copy().sets.map { it.green }.filterNotNull().maxOrNull()
        val minBlue = game.copy().sets.map { it.blue }.filterNotNull().maxOrNull()
        minRed!! * minGreen!! * minBlue!!
    }

fun List<Game>.sumValidGameIds(): Int {
    return filter { it.isValid }.sumOf { it.id }
}
