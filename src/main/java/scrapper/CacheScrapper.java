package scrapper;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CacheScrapper implements Scrapper {
    private static DefaultScrapper defaultScrapper = new DefaultScrapper();

    @Override @SneakyThrows
    public Home scrape(String url) {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:db.db");
        Statement statement = connection.createStatement();
        String query = String.format("select count(*) as count from homes where url='%s'", url);
        int count = statement.executeQuery(query).getInt("count");
        if (count > 0) {
            query = String.format(
                    "SELECT url, price, bedCount, bathCount, garageCount FROM homes WHERE url='%s';", url
            );
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return new Home(
                    resultSet.getString("url"),
                    resultSet.getInt("price"),
                    resultSet.getInt("bedCount"),
                    resultSet.getFloat("bathCount"),
                    resultSet.getInt("garageCount")
            );
        } else {
            Home home = defaultScrapper.scrape(url);
            query = String.format(
                    "INSERT INTO homes(url, price, bedCount, bathCount, garageCount) VALUES ('%s', %d, %d, %f, %d);",
                    home.getUrl(), home.getPrice(), home.getBedCount(), home.getBathCount(), home.getGarageCount()
            );
            statement = connection.createStatement();
            statement.executeQuery(query);
        }
        return null;
    }
}
