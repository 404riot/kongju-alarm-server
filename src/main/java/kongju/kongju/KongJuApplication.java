package kongju.kongju;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class KongJuApplication {

    public static void main(String[] args) {

        SpringApplication.run(KongJuApplication.class, args);

        String firstUrl;
        String secondUrl;
        String endUrl;
        String allUrl;

        int pageIndex = 1;

        try {

            while (pageIndex <= 100) {

                // 국제학부
                Document doc = Jsoup.connect("https://www.kongju.ac.kr/kor/article/student_news/?mno=&pageIndex=" + pageIndex + "&categoryCnt=1&searchCategory=&searchCategory0=&searchCondition=1&searchKeyword=").get();

                Elements elements = doc.getElementsByClass("board").select("ul").select("li");

                for (Element elem : elements) {

                    String imageBaseUrl = "https://www.kongju.ac.kr/";

                    // ul 상단 목차 들어가는거 방지.
                    if (!elem.attr("class").equals("thead")) {

                        firstUrl = "https://www.kongju.ac.kr/kor/article/student_news/";
                        secondUrl = elem.select("a").attr("onclick").replaceAll("[^0-9]", "");
                        endUrl = "?mno=&pageIndex=1&categoryCnt=1&searchCategory=&searchCategory0=&searchCondition=1&searchKeyword=#article";
                        allUrl = firstUrl + secondUrl + endUrl;

                        Document detailDoc = Jsoup.connect(allUrl).get();
                        Elements detailElem = detailDoc.getElementsByClass("board_view div_student_news");

                        Elements imageElements = detailElem.select("img");
                        String imgUrl = imageElements.attr("src");
                        imageBaseUrl += imgUrl;

                        if (pageIndex > 1) {
                            // 2번 페이지부터 고정 공지 중복 방지.
                            if (!elem.attr("class").equals("notice")) {
                                System.out.println(detailElem.text());
                                System.out.println(imageBaseUrl);
                            }
                        } else {
                            // 1번 페이지는 일단 때려박음.
                            System.out.println(imageBaseUrl);
                            System.out.println(detailElem.text());
                        }
                    }
                }
                pageIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
