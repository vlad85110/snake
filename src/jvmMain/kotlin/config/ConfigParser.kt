package config

import java.io.FileReader
import java.io.IOException
import java.util.*

class ConfigParse {
    @Throws(IOException::class)
    fun readFile(fileName: String): HashMap<String, String> {
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