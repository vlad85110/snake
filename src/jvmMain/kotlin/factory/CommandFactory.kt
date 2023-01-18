package factory

import config.ConfigParser
import controller.command.Command
import exception.IncorrectCommandException

class CommandFactory {
    private val names: Map<String, String>

    init {
        val parser = ConfigParser()
        names = parser.commandNames
    }

    @Throws(IncorrectCommandException::class)
    fun createCommand(commandName: String): Command {
        try {
            val className = names[commandName]!!
            val productClass = Class.forName("controller.command." + className)
            return productClass.getConstructor().newInstance() as Command
        } catch (e: ClassNotFoundException) {
            throw IncorrectCommandException("no such class")
        } catch (e: NullPointerException) {
            throw IncorrectCommandException("no command name in commands.properties")
        }
    }
}