package view.graphics.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import controller.command.args.CommandArgs
import controller.command.args.JoinArgs
import model.Action
import model.GameAnnouncement
import model.NodeRole
import model.PlayerType
import model.message.JoinMessage

@Composable
fun Games(gameAnnouncements: List<GameAnnouncement>, setAction: (Action, CommandArgs) -> Unit) {
    Column {
        for (announcement in gameAnnouncements) {
            val master = announcement.master

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${master.name}[${master.ipAddress}]")
                Text("${announcement.gamePlayers.size}")
                Text("${announcement.gameConfig.height}х${announcement.gameConfig.width}")
                Text("${announcement.gameConfig.foodStatic}")
                Button(
                    onClick = {
                        setAction(
                            Action.JOIN,
                            JoinArgs(JoinMessage(PlayerType.HUMAN, "stas", announcement.gameName,
                                NodeRole.NORMAL)
                            )
                        )
                    }) {
                    Text("Join")
                }
            }
        }
    }
}