package model.message

import model.NodeRole

class RoleChangeMessage(val senderRole: NodeRole, val receiverRole: NodeRole): GameMessage() {
}