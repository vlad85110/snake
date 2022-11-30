package factory

import config.ConfigParse
import controller.command.Command
import model.Action

class CommandFactory {
    private val names: Map<String, String>

    init {
        val parser = ConfigParse()
        names = parser.readFile("src/jvmMain/resources/commands.properties")
    }

    fun createCommand(action: Action): Command {
        val productClass: Class<*>
        val actionStr = action.toString()
        productClass = Class.forName("controller.command.snake." + names[actionStr])
        //todo нормальный поиск по пакетам
        return productClass.getConstructor().newInstance() as Command
    }
}