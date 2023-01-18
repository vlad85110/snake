package view

import model.GameAnnouncement
import model.GameState
import model.field.Field

interface View {
    fun updateField(field: Field)
    fun showLoseMessage()
    fun updateGameList(games: List<GameAnnouncement>)
    fun updateGameState(state: GameState)
}