package server

interface Server: Runnable {
    fun runNewGame(game: Game)
    fun stopGame(name: String)
}