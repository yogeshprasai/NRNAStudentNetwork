package org.nrna.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.nrna.models.dto.News;
import org.nrna.models.dto.University;
import org.nrna.models.news.NewsResult;
import org.nrna.models.news.SerpApi;
import org.nrna.repository.MiscRepository;
import org.nrna.repository.UniversityRepository;
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

    @Autowired
    UniversityRepository universityRepository;

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
//        String newsJSON = "<200,{\n" +
//                "    \"search_metadata\": {\n" +
//                "        \"id\": \"67b7363d251b09e08bd89482\",\n" +
//                "        \"status\": \"Success\",\n" +
//                "        \"json_endpoint\": \"https://serpapi.com/searches/7280a941a4ca04f4/67b7363d251b09e08bd89482.json\",\n" +
//                "        \"created_at\": \"2025-02-20 14:03:41 UTC\",\n" +
//                "        \"processed_at\": \"2025-02-20 14:03:41 UTC\",\n" +
//                "        \"google_url\": \"https://www.google.com/search?q=Nepali+Student&oq=Nepali+Student&uule=w+CAIQICINVW5pdGVkIFN0YXRlcw&hl=en&gl=us&num=100&tbm=nws&sourceid=chrome&ie=UTF-8\",\n" +
//                "        \"raw_html_file\": \"https://serpapi.com/searches/7280a941a4ca04f4/67b7363d251b09e08bd89482.html\",\n" +
//                "        \"total_time_taken\": 1.56\n" +
//                "    },\n" +
//                "    \"search_parameters\": {\n" +
//                "        \"engine\": \"google\",\n" +
//                "        \"q\": \"Nepali Student\",\n" +
//                "        \"location_requested\": \"United States\",\n" +
//                "        \"location_used\": \"United States\",\n" +
//                "        \"google_domain\": \"google.com\",\n" +
//                "        \"hl\": \"en\",\n" +
//                "        \"gl\": \"us\",\n" +
//                "        \"num\": \"100\",\n" +
//                "        \"device\": \"desktop\",\n" +
//                "        \"tbm\": \"nws\"\n" +
//                "    },\n" +
//                "    \"search_information\": {\n" +
//                "        \"query_displayed\": \"Nepali Student\",\n" +
//                "        \"total_results\": 139000,\n" +
//                "        \"time_taken_displayed\": 0.31,\n" +
//                "        \"news_results_state\": \"Results for exact spelling\"\n" +
//                "    },\n" +
//                "    \"people_also_search_for\": [\n" +
//                "        {\n" +
//                "            \"name\": \"Nepali student's death at KIIT University\",\n" +
//                "            \"news_results\": [\n" +
//                "                {\n" +
//                "                    \"position\": 1,\n" +
//                "                    \"link\": \"https://timesofindia.indiatimes.com/city/bhubaneswar/suicide-protests-evictions-and-arrests-how-a-nepali-students-death-sparked-chaos-at-odishas-kiit/articleshow/118383230.cms\",\n" +
//                "                    \"title\": \"Suicide, protests, evictions and arrests: How a Nepali student’s death sparked chaos at Odisha’s KIIT\",\n" +
//                "                    \"source\": \"Times of India\",\n" +
//                "                    \"date\": \"1 day ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21ff393a76826a6c9f566bdc6fa3ad50fb1a0c0906041e63466.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 2,\n" +
//                "                    \"link\": \"https://www.thehindu.com/news/national/odisha-assures-stern-action-against-kiit-over-nepali-students-death-on-campus/article69238365.ece\",\n" +
//                "                    \"title\": \"KIIT student death row: Odisha assures stern action against university over Nepali student’s death on campus\",\n" +
//                "                    \"source\": \"The Hindu\",\n" +
//                "                    \"date\": \"1 day ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21fbb8e1d57717a971958419d55668b7226c26496779bb82470.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 3,\n" +
//                "                    \"link\": \"https://www.the-independent.com/asia/india/kiit-university-odisha-student-suicide-protest-nepal-b2699889.html\",\n" +
//                "                    \"title\": \"Death of 20-year-old Nepali student at Indian university sparks protests and diplomatic outcry\",\n" +
//                "                    \"source\": \"The Independent\",\n" +
//                "                    \"date\": \"2 days ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21fea6513657c2b4f685f8ef73fc100896c5ed99ebe5281f296.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 4,\n" +
//                "                    \"link\": \"https://www.ndtv.com/india-news/kiit-prakriti-lamsal-sent-daughter-here-for-higher-studies-father-of-nepal-student-who-died-by-suicide-7738871\",\n" +
//                "                    \"title\": \"\\\"Sent Daughter Here For Higher Studies\\\": Father Of Nepal Student Who Died By Suicide At KIIT\",\n" +
//                "                    \"source\": \"NDTV\",\n" +
//                "                    \"date\": \"1 day ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21ffb3727126c3e16f6e5d3e681b88eb33cf1ca5c56cd5cc1f9.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 5,\n" +
//                "                    \"link\": \"https://www.dw.com/en/kiit-nepalese-students-death-sparks-anger-at-india-college/a-71642337\",\n" +
//                "                    \"title\": \"KIIT: Nepalese student's death sparks anger at India college\",\n" +
//                "                    \"source\": \"DW\",\n" +
//                "                    \"date\": \"2 days ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21fdb72215839a8dc0040267913a0f8b74c11e70994d83594ea.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 6,\n" +
//                "                    \"link\": \"https://www.thenewsminute.com/news/diplomatic-row-erupts-as-kiit-evicts-500-nepali-students-for-protests-over-suicide\",\n" +
//                "                    \"title\": \"Diplomatic row erupts as KIIT evicts 500 Nepali students for protests over suicide\",\n" +
//                "                    \"source\": \"The News Minute\",\n" +
//                "                    \"date\": \"2 days ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21fcc6d6717d45bf7f66678bcce363e551dd3db580c05e2031a.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 7,\n" +
//                "                    \"link\": \"https://m.economictimes.com/news/new-updates/kiit-students-death-nepali-students-protest-over-kiit-students-death-what-happened/articleshow/118334930.cms\",\n" +
//                "                    \"title\": \"KIIT Student's Death: Nepali students protest over KIIT student’s death: What happened?\",\n" +
//                "                    \"source\": \"The Economic Times\",\n" +
//                "                    \"date\": \"2 days ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21f29026ac655f9da368fae2d556ccecf0e1296b21e39163f5a.jpeg\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"position\": 8,\n" +
//                "                    \"link\": \"https://newschecker.in/fact-check/kiit-nepal-student-suicide-viral-screenshot-misidentifies-lucknow-politician-as-father-of-accused\",\n" +
//                "                    \"title\": \"Fact Check: KIIT Nepal Student Suicide: Viral Screenshot Misidentifies Lucknow Politician As Father Of Accused\",\n" +
//                "                    \"source\": \"Newschecker\",\n" +
//                "                    \"date\": \"11 hours ago\",\n" +
//                "                    \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/c655a44c8957be8d866623f6456b8cb3ed2809d0b6c3f9ac4a25848264a1a21f17cac1612a7a61fcdfffacc0c1e544841e40f6e97a688942.jpeg\"\n" +
//                "                }\n" +
//                "            ],\n" +
//                "            \"view_full_coverage_link\": \"https://www.google.com/search?q=Nepali+Student&num=100&sca_esv=8a5f3c529fc99551&hl=en&gl=us&tbm=nws&story=GisSKU5lcGFsaSBzdHVkZW50J3MgZGVhdGggYXQgS0lJVCBVbml2ZXJzaXR5MjEKJ_nMjN-fv5mCeOjMiM3ui-2LtQGLvILj5oXMhrUB0YuetIO8rPO5ARCH_4umDRgFcgIQAg&fcs=AJQC52yQG8aoM4PZv41bhB_n_xmuUK-HPg&sa=X&ved=2ahUKEwiVg8q7tdKLAxVBGtAFHVZZEeIQ7IUHegQICxAF\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"news_results\": [\n" +
//                "        {\n" +
//                "            \"position\": 1,\n" +
//                "            \"link\": \"https://ommcomnews.com/odisha-news/nepals-foreign-minister-follows-up-on-nepali-students-death-at-kiit-university/\",\n" +
//                "            \"title\": \"Nepal’s Foreign Minister Follows Up On Nepali Student’s Death At KIIT University\",\n" +
//                "            \"source\": \"Ommcom News\",\n" +
//                "            \"date\": \"21 hours ago\",\n" +
//                "            \"snippet\": \"Bhubaneswar: The Minister of Foreign Affairs of Nepal, Dr. Arzu Rana Deuba on Wednesday spoke with Odisha's Higher Education Minister...\",\n" +
//                "            \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/17d581f941b56ec428d7edf4921f722ca1ccdcff98b4a1277afa30108c76c14c.jpeg\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"position\": 91,\n" +
//                "            \"link\": \"https://www.timesofisrael.com/family-friends-fear-for-fate-of-nepali-hostage-bipin-joshi-he-saved-our-lives/\",\n" +
//                "            \"title\": \"Family, friends fear for fate of Nepali hostage Bipin Joshi: ‘He saved our lives’\",\n" +
//                "            \"source\": \"The Times of Israel\",\n" +
//                "            \"date\": \"2 days ago\",\n" +
//                "            \"snippet\": \"24-year-old agriculture student came to Israel 2 months before Oct. 7; he deflected grenade thrown at sheltering farmhands; is not slated...\",\n" +
//                "            \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/17d581f941b56ec482da674c8f2d8d3ca63397b0c98600b6f63807e736f186f8.jpeg\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"position\": 92,\n" +
//                "            \"link\": \"https://www.business-standard.com/india-news/kiit-nepali-girl-suicide-case-lucknow-odisha-kp-sharma-oli-student-protest-125021800122_1.html\",\n" +
//                "            \"title\": \"KIIT suicide row: Nepal PM steps in; accused held - what we know so far\",\n" +
//                "            \"source\": \"Business Standard\",\n" +
//                "            \"date\": \"2 days ago\",\n" +
//                "            \"snippet\": \"KIIT University Bhubaneswar Prakriti Lamsal Death Case: A third-year BTech student from Nepal was found dead in her hostel room,...\",\n" +
//                "            \"thumbnail\": \"https://serpapi.com/searches/67b7363d251b09e08bd89482/images/17d581f941b56ec4ce82d15297c37f57f28a0b19b8a7f4bdbb45c555ff6e586f.jpeg\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"pagination\": {\n" +
//                "        \"current\": 1,\n" +
//                "        \"next\": \"https://www.google.com/search?q=Nepali+Student&num=100&sca_esv=8a5f3c529fc99551&hl=en&gl=us&tbm=nws&ei=Pja3Z5XsG8G0wN4P1rLFkA4&start=100&sa=N&ved=2ahUKEwiVg8q7tdKLAxVBGtAFHVZZEeIQ8NMDegQIAxAI\",\n" +
//                "        \"other_pages\": {\n" +
//                "            \"2\": \"https://www.google.com/search?q=Nepali+Student&num=100&sca_esv=8a5f3c529fc99551&hl=en&gl=us&tbm=nws&ei=Pja3Z5XsG8G0wN4P1rLFkA4&start=100&sa=N&ved=2ahUKEwiVg8q7tdKLAxVBGtAFHVZZEeIQ8tMDegQIAxAE\",\n" +
//                "            \"3\": \"https://www.google.com/search?q=Nepali+Student&num=100&sca_esv=8a5f3c529fc99551&hl=en&gl=us&tbm=nws&ei=Pja3Z5XsG8G0wN4P1rLFkA4&start=200&sa=N&ved=2ahUKEwiVg8q7tdKLAxVBGtAFHVZZEeIQ8tMDegQIAxAG\"\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"serpapi_pagination\": {\n" +
//                "        \"current\": 1,\n" +
//                "        \"next_link\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&num=100&q=Nepali+Student&start=100&tbm=nws\",\n" +
//                "        \"next\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&num=100&q=Nepali+Student&start=100&tbm=nws\",\n" +
//                "        \"other_pages\": {\n" +
//                "            \"2\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&num=100&q=Nepali+Student&start=100&tbm=nws\",\n" +
//                "            \"3\": \"https://serpapi.com/search.json?device=desktop&engine=google&gl=us&google_domain=google.com&hl=en&location=United+States&num=100&q=Nepali+Student&start=200&tbm=nws\"\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
        System.out.println(newsJSON);
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

    public ArrayList<University> getTopUniversities(){
        return (ArrayList<University>)universityRepository.findAll();
    }
}
