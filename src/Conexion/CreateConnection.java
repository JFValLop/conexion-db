package Conexion;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateConnection {

    private Properties config = new Properties();

    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;

    public CreateConnection() {
        String path = "src/Conexion/db_config.properties";
        try (InputStream in = Files.newInputStream(Paths.get(path))) {
            config.load(in);
            loadProperties();
        } catch (IOException ex) {
            System.err.println("Error al cargar archivo properties: " + ex.getMessage());
        }
    }

    private void loadProperties() {
        this.hostname = config.getProperty("hostname");
        this.port     = config.getProperty("port");
        this.username = config.getProperty("username");
        this.password = config.getProperty("password");
        this.database = config.getProperty("database");
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + database + "?sslmode=disable";
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Conexion establecida");
        } catch (SQLException ex) {
            Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, "Error de SQL", ex);
        }
        return conn;
    }
}
