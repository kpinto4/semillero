package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:postgresql://ep-blue-paper-ad9mulhq-pooler.c-2.us-east-1.aws.neon.tech/cacao_bodega?sslmode=require&channel_binding=require";
    

    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_zAjk7EGvF3XH";

   
     
    public static Connection getConnection() throws SQLException {
        try {
           
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de PostgreSQL.");
        }

      
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("✅ Conexión exitosa a la base de datos Neon.");
        return connection;
    }
}
