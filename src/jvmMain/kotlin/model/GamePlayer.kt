package model

data class GamePlayer(
    val name: String,
    var nodeRole: NodeRole,
    val playerType: PlayerType = PlayerType.HUMAN
) {
    var ipAddress: String? = null
    var port: Int? = null
    var id: Int = 0
    var score: Int = 0
}