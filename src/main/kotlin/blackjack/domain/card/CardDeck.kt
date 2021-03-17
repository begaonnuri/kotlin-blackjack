package blackjack.domain.card

object CardDeck {
    private val _cards: List<Card> = initCard()
    val cards: List<Card>
        get() = _cards.toList()

    private fun initCard() = CardSymbol.values().flatMap { symbol -> CardSuit.values().map { Card(symbol, it) } }
}