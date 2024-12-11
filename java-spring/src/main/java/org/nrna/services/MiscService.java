package org.nrna.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.nrna.models.dto.News;
import org.nrna.models.news.NewsResult;
import org.nrna.models.news.SerpApi;
import org.nrna.repository.MiscRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MiscService {

    @Autowired
    MiscRepository miscRepository;

    public ResponseEntity<?> getLatestNews(){
        ArrayList<NewsResult> allNewsFromSerpApi = null;
        ArrayList<News> allNewsFromDb = getAllNewsFromDB();

        if(allNewsFromDb.isEmpty()) {
            allNewsFromSerpApi = (ArrayList<NewsResult>) getLatestNewsFromSerpApi();
            if(!allNewsFromSerpApi.isEmpty()) {
                persistAllNews(allNewsFromSerpApi, allNewsFromDb);
            }

        }else{
            Collections.sort(allNewsFromDb, new Comparator<News>() {
                public int compare(News o1, News o2) {
                    return o1.getPersistDate().compareTo(o2.getPersistDate());
                }
            });
            //Get when was the latest news pulled in from serpAPI
            //and get the last updated date
            News latestNews = allNewsFromDb.get(allNewsFromDb.size()-1);
            if(LocalDate.parse(latestNews.getPersistDate()).isBefore(LocalDate.now())){
                allNewsFromSerpApi = (ArrayList<NewsResult>) getLatestNewsFromSerpApi();
                if(!allNewsFromSerpApi.isEmpty()){
                    persistAllNews(allNewsFromSerpApi, allNewsFromDb);
                }
            }
        }

        return new ResponseEntity<>(getAllNewsFromDB(), HttpStatus.OK);
    }

    private List<NewsResult> getLatestNewsFromSerpApi(){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://serpapi.com/search.json?engine=google&q=Nepali+Student&location=United+States&google_domain=google.com&gl=us&hl=en&tbm=nws&num=100&api_key=09d5e8b03937bbd246472efff47f4cc672568959397e88630ae46e6201f667cf";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> newsJSON = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        //String newsJSON = "<200,{\n  \"search_metadata\": {\n    \"id\": \"67561c0566d995a1ba33a69b\",\n    \"status\": \"Success\",\n    \"json_endpoint\": \"https://serpapi.com/searches/818a25a6d2795149/67561c0566d995a1ba33a69b.json\",\n    \"created_at\": \"2024-12-08 22:21:57 UTC\",\n    \"processed_at\": \"2024-12-08 22:21:57 UTC\",\n    \"google_url\": \"https://www.google.com/search?q=Nepali+Student&oq=Nepali+Student&uule=w+CAIQICINVW5pdGVkIFN0YXRlcw&hl=en&gl=us&tbm=nws&sourceid=chrome&ie=UTF-8\",\n    \"raw_html_file\": \"https://serpapi.com/searches/818a25a6d2795149/67561c0566d995a1ba33a69b.html\",\n    \"total_time_taken\": 1.21\n  },\n  \"search_parameters\": {\n    \"engine\": \"google\",\n    \"q\": \"Nepali Student\",\n    \"location_requested\": \"United States\",\n    \"location_used\": \"United States\",\n    \"google_domain\": \"google.com\",\n    \"hl\": \"en\",\n    \"gl\": \"us\",\n    \"device\": \"desktop\",\n    \"tbm\": \"nws\"\n  },\n  \"search_information\": {\n    \"query_displayed\": \"Nepali Student\",\n    \"total_results\": 4000,\n    \"time_taken_displayed\": 0.25,\n    \"news_results_state\": \"Results for exact spelling\"\n  },\n  \"news_results\": [\n    {\n      \"position\": 1,\n      \"link\": \"https://www.click2houston.com/news/local/2024/12/04/attorney-says-accused-killer-of-nepali-student-studying-in-houston-may-have-had-intimate-relationship-with-her/\",\n      \"title\": \"Attorney says accused killer of Nepali student studying in Houston may have had ‘intimate’ relationship with her\",\n      \"source\": \"Click2Houston\",\n      \"date\": \"4 days ago\",\n      \"snippet\": \"New details have been revealed in the capital murder case of Bobby Shah.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766560fa7000cd1dfde24d10f256508ab6634f835c6c6f2d0f2.jpeg\"\n    },\n    {\n      \"position\": 2,\n      \"link\": \"https://www.khou.com/article/news/crime/muna-pandey-accused-killer-court-appearance/285-749d6ec1-8cf0-49a5-933c-a5d0f74a0d39\",\n      \"title\": \"Attorney reveals accused killer may have had intimate relationship with 21-year-old college student\",\n      \"source\": \"KHOU\",\n      \"date\": \"4 days ago\",\n      \"snippet\": \"HOUSTON — New details were revealed in court on Tuesday in the case of a \\n21-year-old Nepali college student who was killed this summer.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c7667aabf20ea72bb9e8b46bea83fe46ee7b5916fb00b1b9b4f4.jpeg\"\n    },\n    {\n      \"position\": 3,\n      \"link\": \"https://kathmandupost.com/art-culture/2024/12/08/affordable-places-for-nepali-students-in-melbourne\",\n      \"title\": \"Affordable places for Nepali students in Melbourne\",\n      \"source\": \"The Kathmandu Post\",\n      \"date\": \"20 hours ago\",\n      \"snippet\": \"Looking to study in one of Australia's major cities? These five areas offer \\ncheap rents and convenient commutes.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766c32a5b367f5e6b6f12d2573045142d4839abc81897d06b5c.jpeg\"\n    },\n    {\n      \"position\": 4,\n      \"link\": \"https://myrepublica.nagariknetwork.com/news/israeli-ambassador-bass-confirms-nepali-student-bipin-joshi-abducted-by-ham...-6751893d7356f.html\",\n      \"title\": \"Israeli Ambassador Bass confirms Nepali student Bipin Joshi, abducted by Hamas, is alive\",\n      \"source\": \"My Republica\",\n      \"date\": \"2 days ago\",\n      \"snippet\": \"A total of 10 Nepali students, who were in Israel as a part of the Earn and \\nLearn program in Israel were brutally murdered by the Hamas...\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c76624b5db1685c7164c8d652f76ae142ca356ebf762bb4a4f43.jpeg\"\n    },\n    {\n      \"position\": 5,\n      \"link\": \"https://www.houstonchronicle.com/news/houston-texas/crime/article/bobby-shah-gun-muna-pandey-houston-college-19957704.php\",\n      \"title\": \"Man accused of killing Houston student bought gun that night, prosecutors say\",\n      \"source\": \"Houston Chronicle\",\n      \"date\": \"4 days ago\",\n      \"snippet\": \"A man accused in the death of Houston college student Muna Pandey, whom he \\nhad known for months, bought a handgun hours before confronting...\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766329ff7491a87c1b60e83e766530f39db419eac53d5634725.jpeg\"\n    },\n    {\n      \"position\": 6,\n      \"link\": \"https://timesofindia.indiatimes.com/world/us/indian-origin-man-arrested-for-alleged-murder-of-nepali-student-in-texas-after-sugar-daddy-site-tip/articleshow/112940890.cms\",\n      \"title\": \"Indian-origin man arrested for alleged murder of Nepali student in Texas after 'Sugar Daddy' site tip\",\n      \"source\": \"Times of India\",\n      \"date\": \"Aug 31, 2024\",\n      \"snippet\": \"US News: Bobby Singh Shah, an Indian-origin man, has been arrested for \\nallegedly murdering 21-year-old Nepalese student Muna Pandey in...\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766f96fd51b5ec0faf8b3f18069bc9574fc4057cdfa5f20afd5.jpeg\"\n    },\n    {\n      \"position\": 7,\n      \"link\": \"https://www.hindustantimes.com/world-news/us-news/nepali-student-21-found-shot-to-death-in-her-houston-apartment-man-arrested-and-charged-101724951215988.html\",\n      \"title\": \"Nepali student, 21, found shot to death in her Houston apartment; Man arrested and charged\",\n      \"source\": \"Hindustan Times\",\n      \"date\": \"Aug 29, 2024\",\n      \"snippet\": \"Muna Pandey, a 21-year-old college student from Nepal, was found dead with \\nmultiple gunshot wounds in her Houston apartment.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766ca57e2c4032a12e0dfdd48af1d2bb0d9a8bb94c907d4fcc0.jpeg\"\n    },\n    {\n      \"position\": 8,\n      \"link\": \"https://monitor.icef.com/2024/10/market-snapshot-international-student-recruitment-in-nepal/\",\n      \"title\": \"Market snapshot: International student recruitment in Nepal\",\n      \"source\": \"ICEF Monitor\",\n      \"date\": \"1 month ago\",\n      \"snippet\": \"From 23rd place in 2022 (40,560), Nepal is projected to become the 7th \\nlargest sender of students in 2025 (113,395).\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c766f9fc2d3b1f4614581a7cc5f3d2c578d9c376501fc550eaea.jpeg\"\n    },\n    {\n      \"position\": 9,\n      \"link\": \"https://www.newsx.com/world/indian-origin-man-arrested-for-killing-21-year-old-nepalese-student-during-robbery-in-us-apartment/\",\n      \"title\": \"Indian-Origin Man Arrested For Killing 21-Year-Old Nepalese Student During Robbery in US Apartment\",\n      \"source\": \"Newsx\",\n      \"date\": \"Aug 31, 2024\",\n      \"snippet\": \"A 21-year-old Nepali student, Muna Pandey, was tragically shot dead during \\na robbery at her apartment in Houston, Texas.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c7663a43086c7407ffc14f1f88a0e3756f7973f606de64f5d541.jpeg\"\n    },\n    {\n      \"position\": 10,\n      \"link\": \"https://www.click2houston.com/news/local/2024/08/30/bail-denied-for-suspect-accused-of-killing-21-year-old-nepali-student-studying-nursing-in-houston/\",\n      \"title\": \"Bail denied for suspect accused of killing 21-year-old Nepali student studying nursing in Houston\",\n      \"source\": \"Click2Houston\",\n      \"date\": \"Aug 29, 2024\",\n      \"snippet\": \"Bail has been denied for a suspect accused of killing a 21-year-old student \\nfrom Nepal who was studying nursing in Houston.\",\n      \"thumbnail\": \"https://serpapi.com/searches/67561c0566d995a1ba33a69b/images/07063d46c996c7669e48671c9837ddea7c08c890578ff87e99380844cef7e448.jpeg\"\n    }\n  ],\n  \"pagination\": {\n    \"current\": 1,\n    \"next\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=10&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8NMDegQIBRAW\",\n    \"other_pages\": {\n      \"2\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=10&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAE\",\n      \"3\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=20&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAG\",\n      \"4\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=30&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAI\",\n      \"5\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=40&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAK\",\n      \"6\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=50&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAM\",\n      \"7\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=60&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAO\",\n      \"8\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=70&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAQ\",\n      \"9\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=80&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAS\",\n      \"10\": \"https://www.google.com/search?q=Nepali+Student&sca_esv=face10566ce46e9f&hl=en&gl=us&tbm=nws&ei=BhxWZ9TTH-LT5NoPu-3KqQM&start=90&sa=N&ved=2ahUKEwjU7onpmpmKAxXiKVkFHbu2MjUQ8tMDegQIBRAU\"\n    }\n  },\n  \"serpapi_pagination\": {\n    \"current\": 1,\n    \"next_link\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=10&tbm=nws\",\n    \"next\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=10&tbm=nws\",\n    \"other_pages\": {\n      \"2\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=10&tbm=nws\",\n      \"3\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=20&tbm=nws\",\n      \"4\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=30&tbm=nws\",\n      \"5\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=40&tbm=nws\",\n      \"6\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=50&tbm=nws\",\n      \"7\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=60&tbm=nws\",\n      \"8\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=70&tbm=nws\",\n      \"9\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=80&tbm=nws\",\n      \"10\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&q=Nepali+Student&start=90&tbm=nws\"\n    }\n  }\n},[Date:\"Sun, 08 Dec 2024 22:27:46 GMT\", Content-Type:\"application/json; charset=utf-8\", Content-Length:\"11556\", Connection:\"keep-alive\", x-frame-options:\"SAMEORIGIN\", x-xss-protection:\"1; mode=block\", x-content-type-options:\"nosniff\", x-download-options:\"noopen\", x-permitted-cross-domain-policies:\"none\", referrer-policy:\"strict-origin-when-cross-origin\", x-robots-tag:\"noindex, nofollow\", serpapi-search-id:\"67561c0566d995a1ba33a69b\", cache-control:\"max-age=3600, public\", etag:\"W/\"da4767dbd0d5a4579d4ea5685823f1a5\"\", x-request-id:\"16384c10-7b0c-4e5c-88a3-df7c12281bd5\", x-runtime:\"1.308921\", CF-Cache-Status:\"HIT\", Age:\"347\", Accept-Ranges:\"bytes\", Server:\"cloudflare\", CF-RAY:\"8ef02f44ab5a0f9b-EWR\", alt-svc:\"h3=\":443\"; ma=86400\"]>";        System.out.println(newsJSON);
        System.out.println(newsJSON);
        ObjectMapper objectMapper = new ObjectMapper();
        SerpApi serpApiNews = new SerpApi();
        try {
            serpApiNews = objectMapper.readValue(newsJSON.toString().substring(5, newsJSON.toString().length()), SerpApi.class);
        } catch (Exception e) {
            System.out.println("Error Parsing News Result" + e.getMessage());
        }
        return serpApiNews.getNewsResults();
    }

    //Check if news exist in db
    //If news exist, don't persist to all news object, just persist latest date
    private void persistAllNews(ArrayList<NewsResult> allNewsFromSerpApi, ArrayList<News> allNewsFromDb){
        if(!allNewsFromSerpApi.isEmpty()){
            for(NewsResult eachNewsToPersist : allNewsFromSerpApi){
                boolean newsExist = false;
                if(!allNewsFromDb.isEmpty()){
                    newsExist = allNewsFromDb.stream().anyMatch(n -> {
                        if(n.getLink().equals(eachNewsToPersist.getLink())){
                            n.setPersistDate(LocalDate.now().toString());
                            miscRepository.save(n);
                            return true;
                        };
                        return false;
                    });
                }
                if(!newsExist){
                    News news = new News();
                    news.setTitle(eachNewsToPersist.getTitle());
                    news.setLink(eachNewsToPersist.getLink());
                    news.setTitle(eachNewsToPersist.getTitle());
                    news.setSource(eachNewsToPersist.getSource());
                    news.setNewsDate(eachNewsToPersist.getDate());
                    news.setPersistDate(LocalDate.now().toString());
                    news.setSnippet(eachNewsToPersist.getSnippet());
                    news.setThumbnail(eachNewsToPersist.getThumbnail());
                    miscRepository.save(news);
                }
            }
        }

    }

    private @NotNull ArrayList<News> getAllNewsFromDB() {
        return (ArrayList<News>) miscRepository.findAll();
    }
}
