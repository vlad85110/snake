package config

import exception.ReadConfigException
import java.io.FileReader
import java.util.*

class ConfigParser {
    private val resourceFolderPath = "src/jvmMain/resources/"

    val commandNames: Map<String, String>
    val multicastAddress: String
    val multicastPort: Int
    val gameScanPeriod: Long
    val gameAnnouncePeriod: Long

    init {
        commandNames = readFile(resourceFolderPath + "commands.properties")
        val general = readFile(resourceFolderPath + "general.properties")
        try {
            multicastAddress = general["address"]!!
        } catch (e: NullPointerException) {
            throw ReadConfigException("no address")
        }

        try {
            multicastPort = general["port"]!!.toInt()
        } catch (e: NullPointerException) {
            throw ReadConfigException("no port")
        } catch (e: NumberFormatException) {
            throw ReadConfigException("incorrect port")
        }

        try {
            gameScanPeriod = general["scanPeriod"]!!.toLong()
        } catch (e: NullPointerException) {
            throw ReadConfigException("no scan period")
        } catch (e: NumberFormatException) {
            throw ReadConfigException("incorrect scan period")
        }

        try {
            gameAnnouncePeriod = general["announcePeriod"]!!.toLong()
        } catch (e: NullPointerException) {
            throw ReadConfigException("no announce period")
        } catch (e: NumberFormatException) {
            throw ReadConfigException("incorrect announce period")
        }
    }

    private fun readFile(fileName: String): Map<String, String> {
        val properties = Properties()
        val reader = FileReader(fileName)
        properties.load(reader)

        val config = HashMap<String, String>()
        for (i in properties.stringPropertyNames()) {
            config[i] = properties.getProperty(i)
        }

        return config
    }
}