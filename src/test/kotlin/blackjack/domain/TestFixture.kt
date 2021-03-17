package blackjack.domain

import blackjack.domain.card.Card
import blackjack.domain.card.CardSuit
import blackjack.domain.card.CardSymbol
import blackjack.domain.player.Player
import blackjack.domain.player.PlayerName

fun createPlayer(name: String) = Player(PlayerName(name))

fun createCard(symbol: String, suit: String) = Card(CardSymbol.valueOf(symbol), CardSuit.valueOf(suit))