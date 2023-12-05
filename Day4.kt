import utils.readFileLineByLineUsingForEachLine

data class Card(
    val winningNumbers: Set<Int>,
    val possesedNumbers: Set<Int>,
    var counter: Int = 1
)

fun main() {
    val input: List<String> = readFileLineByLineUsingForEachLine("./inputFiles/day4.txt")
    firstPart(input)
    secondPart(input)
}

fun firstPart(cards: List<String>) {
    var result = 0
    cards.forEachIndexed { index, card ->
        val (winningNumbersStr, numbersPossesedStr) = card.split(':')[1].split('|')
        val winningNumbers = winningNumbersStr.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        var numberOfMatches = 0

        numbersPossesedStr.split(' ').filter { it.isNotBlank() }.map { it.toInt() }.forEach {
            if (winningNumbers.contains(it)) {
                numberOfMatches = numberOfMatches.inc()
            }
        }
        var acc = 1
        if (numberOfMatches != 0) {
            while (numberOfMatches > 1) {
                acc *= 2
                numberOfMatches--
            }
            result += acc
        }
    }
    println(result)
}

fun secondPart(input: List<String>) {
    val cards: MutableMap<Int, Card> = mutableMapOf()
    input.forEachIndexed { index, card ->
        val (winningNumbersStr, numbersPossesedStr) = card.split(':')[1].split('|')
        val winningNumbers = winningNumbersStr.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        val possesedNumbers = numbersPossesedStr.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        cards[index + 1] = Card(
            winningNumbers = winningNumbers.toSet(),
            possesedNumbers = possesedNumbers.toSet(),
            1
        )
    }
    cards.toSortedMap()
    cards.forEach { card ->
        var acc = 0
        for (i in 1..card.value.counter) {
            var index = 1
            card.value.possesedNumbers.forEach {
                if (card.value.winningNumbers.contains(it)) {
                    acc += 1
                }
            }
            while (acc > 0) {
                cards[card.key + index]?.counter = cards[card.key + index]?.counter!! + 1
                acc--
                index++
            }
        }
    }

    println(cards.values.sumOf { it.counter })
}
