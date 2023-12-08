import utils.readFileLineByLineUsingForEachLine

enum class Figure(val power: Int) {
    FIVE(7),
    FOUR(6),
    FULL(5),
    THREE(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

val cardValues: Map<Char, Int> = mapOf(
    Pair('A', 14),
    Pair('K', 13),
    Pair('Q', 12),
    // for part 1 change J value to 11
    Pair('J', 1),
    Pair('T', 10),

    Pair('9', 9),
    Pair('8', 8),
    Pair('7', 7),
    Pair('6', 6),
    Pair('5', 5),
    Pair('4', 4),
    Pair('3', 3),
    Pair('2', 2),
)

data class Hand(
    var cards: String,
    val bid: Long,
    var handPower: Int,
    var figure: Figure?,
)

fun main() {

    val hands: List<Hand> = readFileLineByLineUsingForEachLine("./inputFiles/day7.txt").map {
        val (cards, bid) = it.split(' ')
        Hand(cards, bid.toLong(), 0, null)
    }.map { it.calculatePower() }.toList()

    val sortedHands = hands.sortedWith(
        compareBy<Hand> { it.figure }
            .thenByDescending { cardValues[it.cards[0]] }
            .thenByDescending { cardValues[it.cards[1]] }
            .thenByDescending { cardValues[it.cards[2]] }
            .thenByDescending { cardValues[it.cards[3]] }
            .thenByDescending { cardValues[it.cards[4]] }
    ).reversed()

    val result = sortedHands.mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
    println(result)
}

fun MutableMap<Char, Int>.isFive(): Boolean =
    this.containsValue(5)

fun MutableMap<Char, Int>.isFour(): Boolean =
    this.containsValue(4)

fun MutableMap<Char, Int>.isFullHouse(): Boolean =
    this.containsValue(3) && this.containsValue(2)

fun MutableMap<Char, Int>.isThree(): Boolean =
    this.containsValue(3)

fun MutableMap<Char, Int>.isTwoPair(): Boolean =
    (this.values.count { it == 2 } == 2)

fun MutableMap<Char, Int>.isOnePair(): Boolean =
    (this.values.count { it == 2 } == 1)

fun MutableMap<Char, Int>.isHighCard(): Boolean =
    (this.values.sum() == 5)


fun Hand.calculatePower(): Hand {
    var power = 0
    val holder: MutableMap<Char, Int> = mutableMapOf()
    this.cards.toCharArray().forEach {
        if (holder.contains(it)) {
            holder[it] = holder[it]!! + 1
        } else {
            holder[it] = 1
        }
    }

    /*Uncomment this section for part TWO
        if (holder.contains('J')) {
            val max: Int? = holder.filterKeys { it != 'J' }.values.maxOrNull()
            //jockers
            if (max != null) {
                val replacement: Char = holder.filter { it.value == max }.keys.sortedByDescending { cardValues[it] }[0]
                holder[replacement] = holder[replacement]?.plus(holder['J']!!)!!
                holder['J'] = 0
            }
        }
    * */

    if (holder.isFive()) {
        power += Figure.FIVE.power
        this.figure = Figure.FIVE
    } else if (holder.isFour()) {
        power += Figure.FOUR.power
        this.figure = Figure.FOUR
    } else if (holder.isFullHouse()) {
        power += Figure.FULL.power
        this.figure = Figure.FULL
    } else if (holder.isThree()) {
        power += Figure.THREE.power
        this.figure = Figure.THREE
    } else if (holder.isTwoPair()) {
        power += Figure.TWO_PAIR.power
        this.figure = Figure.TWO_PAIR
    } else if (holder.isOnePair()) {
        power += Figure.ONE_PAIR.power
        this.figure = Figure.ONE_PAIR
    } else if (holder.isHighCard()) {
        power += Figure.HIGH_CARD.power
        this.figure = Figure.HIGH_CARD
    }

    var multiplier = 10_000
    for (i in cards) {
        power += if (i.isDigit()) {
            (i.digitToInt() * multiplier)
        } else {
            (cardValues[i]?.times(multiplier) ?: 0)
        }
        multiplier /= 10
    }

    this.handPower = power
    return this
}
