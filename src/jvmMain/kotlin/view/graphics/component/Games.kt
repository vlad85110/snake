package view.graphics.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.GameAnnouncement

@Composable
fun Games(gameAnnouncements: List<GameAnnouncement>) {
    Column {
        for (announcement in gameAnnouncements) {
            val master = announcement.master

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${master.name}[${master.ipAddress}]")
                Text("${announcement.gamePlayers.size}")
                Text("${announcement.gameConfig.height}х${announcement.gameConfig.width}")
                Text("${announcement.gameConfig.foodStatic}")
            }
        }
    }
}