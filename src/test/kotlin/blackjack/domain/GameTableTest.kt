package blackjack.domain

import blackjack.domain.card.CardDeck
import blackjack.domain.card.CardSuit
import blackjack.domain.card.CardSymbol
import blackjack.domain.player.PlayerFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class GameTableTest {
    private lateinit var gameTable: GameTable

    @BeforeEach
    fun setUp() {
        gameTable = GameTable(PlayerFactory.create("pobi"), CardDeck(SORTED_SHUFFLE))
    }

    @DisplayName("게임 준비 단계에서 모든 유저가 2장씩 카드를 뽑는다")
    @Test
    fun prepareGame() {
        val dealerCards = listOf(
            createCard(CardSymbol.KING.name, CardSuit.SPADE.name),
            createCard(CardSymbol.KING.name, CardSuit.HEART.name)
        )
        val playerCards = listOf(
            createCard(CardSymbol.KING.name, CardSuit.DIAMOND.name),
            createCard(CardSymbol.KING.name, CardSuit.CLUB.name)
        )

        gameTable.prepareGame {
            assertAll(
                { assertThat(it.dealer.hands.cards).isEqualTo(dealerCards) },
                { assertThat(it.players.users[0].hands.cards).isEqualTo(playerCards) }
            )
        }
    }

    @DisplayName("DRAW를 인자로 받은 경우 해당 유저가 카드를 뽑고 유저 정보를 반환")
    @Test
    fun proceedGame() {
        gameTable.proceedGame({ DrawDecider.DRAW }, {}) {
            assertAll(
                { assertThat(it.dealer.hands.cards.size).isEqualTo(2) },
                { assertThat(it.players.users.map { it.hands.cards.size }).allMatch { it == 1 } }
            )
        }
    }

    @DisplayName("게임의 결과를 반환")
    @Test
    fun endGame() {
        gameTable.endGame {
            assertAll(
                { assertThat(it.players.map { it.value }).isEqualTo(listOf(ResultType.DRAW)) },
                { assertThat(it.dealer[ResultType.WIN]).isEqualTo(0) },
                { assertThat(it.dealer[ResultType.DRAW]).isEqualTo(1) },
                { assertThat(it.dealer[ResultType.LOSE]).isEqualTo(0) },
            )
        }
    }
}
