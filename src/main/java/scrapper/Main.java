package scrapper;

public class Main {
    private static final String URL =
            "https://www.newhomesource.com/specdetail/24590-sw-119-pl-homestead-fl-33032/2120509";

    public static void main(String[] args) {
        Scrapper defaultScrapper = new CacheScrapper();
        System.out.println(defaultScrapper.scrape(URL));
    }
}
