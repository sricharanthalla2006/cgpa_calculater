package com.example.cgpa; // <-- Must be the first line of code

import com.zaxxer.hikari.HikariConfig; // <-- Import for HikariCP configuration
import com.zaxxer.hikari.HikariDataSource; // <-- Import for HikariCP connection pool

import javax.sql.DataSource; // <-- Import for standard Java SQL DataSource
import java.io.IOException; // <-- Import for handling file I/O errors
import java.io.InputStream; // <-- Import for reading the properties file
import java.util.Properties; // <-- Import for reading key/value properties

public class DBCPool {
    private static HikariDataSource ds;

    static {
        // Loads db.properties and configures the connection pool
        try (InputStream in = DBCPool.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("Error: db.properties file not found in resources folder.");
            }

            Properties props = new Properties();
            props.load(in);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.user"));
            config.setPassword(props.getProperty("jdbc.password"));

            String poolSize = props.getProperty("jdbc.maximumPoolSize");
            if (poolSize != null) {
                config.setMaximumPoolSize(Integer.parseInt(poolSize));
            }

            ds = new HikariDataSource(config);
            System.out.println("✅ Database Connection Pool Initialized.");

        } catch (IOException e) {
            throw new RuntimeException("Unable to load db.properties", e);
        } catch (Exception e) {
            System.err.println("❌ FATAL: Failed to initialize database pool.");
            e.printStackTrace();
            throw new RuntimeException("Database Initialization Error", e);
        }
    }

    /**
     * Provides the DataSource object for getting connections.
     */
    public static DataSource getDataSource() {
        return ds;
    }
}
