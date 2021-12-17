package scrapper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DefaultScrapper implements Scrapper{
    private static final String PRICE_SELECTOR = ".detail__info-xlrg";
    private static final String BED_SELECTOR = ".nhs_BedsInfo";
    private static final String BATH_SELECTOR = ".nhs_BathsInfo";
    private static final String GARAGE_SELECTOR = ".nhs_GarageInfo";

    @Override @SneakyThrows
    public Home scrape(String url) {
        Document doc = Jsoup.connect(url).get();
        return new Home(url, getPrice(doc), getBedCount(doc), getBathCount(doc), getGarageCount(doc));
    }

    private static int getPrice(Document doc) {
        String str = doc.selectFirst(PRICE_SELECTOR).text().replaceAll("[^0-9,-]+", "");
        return Integer.parseInt(str.split("-")[0].replaceAll(",", ""));
    }

    private static int getBedCount(Document doc) {
        String str = doc.selectFirst(BED_SELECTOR).text().split(" ")[0];
        return Integer.parseInt(str);
    }

    private static double getBathCount(Document doc) {
        String str = doc.selectFirst(BATH_SELECTOR).text().split(" ")[0];
        return Double.parseDouble(str);
    }

    private static int getGarageCount(Document doc) {
        String str = doc.selectFirst(GARAGE_SELECTOR).text().split(" ")[0];
        return Integer.parseInt(str);
    }
}
