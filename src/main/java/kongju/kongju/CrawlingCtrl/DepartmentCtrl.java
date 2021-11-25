package kongju.kongju.CrawlingCtrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DepartmentCtrl  {

    public static void departmentCtrl(String url) throws Exception {

        String firstUrl;
        String secondUrl;
        String endUrl;
        String allUrl;
        int pageIndex = 1;
        // 무너가 바또
        // 또 뭔가 바뀜

        while (pageIndex <= 100) {

            // 국제학부
            Document doc = Jsoup.connect("https://www.kongju.ac.kr/kor/article/student_news/?mno=&pageIndex=" + pageIndex + "&categoryCnt=1&searchCategory=&searchCategory0=&searchCondition=1&searchKeyword=").get();
            // 인공지능학부 page1
            Document docAI = Jsoup.connect("https://ai.kongju.ac.kr/ZK0120/6358/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGWkswMTIwJTJGNzQ1JTJGYXJ0Y2xMaXN0LmRvJTNGcGFnZSUzRDElMjZzcmNoQ29sdW1uJTNEJTI2c3JjaFdyZCUzRCUyNmJic0NsU2VxJTNEJTI2YmJzT3BlbldyZFNlcSUzRCUyNnJnc0JnbmRlU3RyJTNEJTI2cmdzRW5kZGVTdHIlM0QlMjZpc1ZpZXdNaW5lJTNEZmFsc2UlMjY%3D").get();
            // 사범대학 RDE RDI RDM 각각 1 2 3 페이지
            Document docCOE = Jsoup.connect("https://sabum.kongju.ac.kr/ZA0000/1255/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGWkEwMDAwJTJGMTU2JTJGYXJ0Y2xMaXN0LmRvJTNGcGFnZSUzRDElMjZzcmNoQ29sdW1uJTNEJTI2c3JjaFdyZCUzRCUyNmJic0NsU2VxJTNEJTI2YmJzT3BlbldyZFNlcSUzRCUyNnJnc0JnbmRlU3RyJTNEJTI2cmdzRW5kZGVTdHIlM0QlMjZpc1ZpZXdNaW5lJTNEZmFsc2UlMjY%3D").get();
            // 국어교육학과 QxJTI 이거 x y z 0 각각 1 2 3 4 페이지
            Document docPHD = Jsoup.connect("https://koredu.kongju.ac.kr/ZA0110/9786/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGWkEwMTEwJTJGMTEwOSUyRmFydGNsTGlzdC5kbyUzRnBhZ2UlM0QxJTI2c3JjaENvbHVtbiUzRCUyNnNyY2hXcmQlM0QlMjZiYnNDbFNlcSUzRCUyNmJic09wZW5XcmRTZXElM0QlMjZyZ3NCZ25kZVN0ciUzRCUyNnJnc0VuZGRlU3RyJTNEJTI2aXNWaWV3TWluZSUzRGZhbHNlJTI2").get();

            Elements elements = doc.getElementsByClass("board").select("ul").select("li");

            for (Element elem : elements) {

                // ul 상단 목차 들어가는거 방지.
                if (!elem.attr("class").equals("thead")) {

                    firstUrl = "https://www.kongju.ac.kr/kor/article/student_news/";
                    secondUrl = elem.select("a").attr("onclick").replaceAll("[^0-9]", "");
                    endUrl = "?mno=&pageIndex=1&categoryCnt=1&searchCategory=&searchCategory0=&searchCondition=1&searchKeyword=#article";
                    allUrl = firstUrl + secondUrl + endUrl;

                    Document detailDoc = Jsoup.connect(allUrl).get();
                    Elements detailElem = detailDoc.getElementsByClass("content");

                    if (pageIndex > 1) {
                        // 2번 페이지부터 고정 공지 중복 방지.
                        if (!elem.attr("class").equals("notice")) {
                            System.out.println("title " + elem.text());
                            System.out.println("content " + detailElem.text());
                        }
                    } else {
                        // 1번 페이지는 일단 때려박음.
                        System.out.println("title " + elem.text());
                        System.out.println("content " + detailElem.text());
                    }
                }
            }
            pageIndex++;
        }
    }
}
