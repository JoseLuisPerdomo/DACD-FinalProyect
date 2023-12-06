package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLWeatherStore implements WeatherStore{

    List<String> tableNames = new ArrayList<>();

    public SQLWeatherStore() {
        this.tableNames = new ArrayList<>();
    }

    public static void createTables(Statement statement, List<String> tableNames) throws StoreException {
        for (int i = 0; i <8; i++){
            try {
                statement.execute(String.format("CREATE TABLE IF NOT EXISTS `%s` (day TEXT PRIMARY KEY, temperature Double, precipitations Double, humidity Double, clouds Double, windSpeed Double);", tableNames.get(i)));
            } catch (SQLException e) {
                throw new StoreException(e.getMessage(), e);
            }
        }
    }

    public static void dropTables(Statement statement, List<String> tableNames) throws StoreException {
        for (int i = 0; i < tableNames.size(); i++) {
            try {
                statement.execute(String.format("DROP TABLE IF EXISTS `%s`;\n", tableNames.get(i)));
            } catch (SQLException e) {
                throw new StoreException(e.getMessage(), e);
            }
        }
    }

    public static void update(Statement statement, String tableName, List<Weather> weatherData) throws StoreException {
            for (int i = 0; i < 5; i++) {
                try {
                    statement.execute(String.format("INSERT OR REPLACE INTO `%s` (day, temperature, precipitations, humidity, clouds, windSpeed) VALUES ('%s', %s, %s, %s, %s, %s);", tableName, weatherData.get(i).getDate(), weatherData.get(i).getTemperature(), weatherData.get(i).getRain(), weatherData.get(i).getHumedity(), weatherData.get(i).getClouds(), weatherData.get(i).getWindSpeed()));
                } catch (SQLException e) {
                    throw new StoreException(e.getMessage(), e);
                }
            }
        System.out.println("Table updated");
    }

    public static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void prepareForStore(String dbPath, List<String> tableNames) throws StoreException{
        try(Connection connection = connect(dbPath)) {
            Statement statement = connection.createStatement();
            dropTables(statement, tableNames);
            createTables(statement, tableNames);

        } catch (SQLException e) {
            throw new StoreException(e.getMessage(), e);
        }
    }

    @Override
    public void StoreData(String dbPath, String tableName, List<Weather> weatherData) throws StoreException {
        try {
            Connection connection = connect(dbPath);
            Statement statement = connection.createStatement();
            update(statement, tableName, weatherData);
        } catch (SQLException e) {
            throw new StoreException(e.getMessage(), e);
        }
    }
}
