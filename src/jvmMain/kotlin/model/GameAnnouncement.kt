package model

class GameAnnouncement(
    val gamePlayers: List<GamePlayer>,
    val gameConfig: GameConfig,
    val canJoin: Boolean = true,
    val gameName: String
) {
    val master: GamePlayer
        get() {
            for (player in gamePlayers) {
                if (player.nodeRole == NodeRole.MASTER) {
                    return player
                }
            }

            return GamePlayer("Stas", NodeRole.MASTER)
        }
}