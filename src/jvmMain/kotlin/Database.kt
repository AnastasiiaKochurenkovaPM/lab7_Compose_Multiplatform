import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object Database {
    private lateinit var connection: Connection

    fun connect(url: String, username: String, password: String) {
        Class.forName("com.mysql.cj.jdbc.Driver")
        connection = DriverManager.getConnection(url, username, password)
    }

    fun executeQuery(query: String): ResultSet {
        val statement: Statement = connection.createStatement()
        return statement.executeQuery(query)
    }

    fun disconnect() {
        if (::connection.isInitialized) {
            connection.close()
        }
    }
}
