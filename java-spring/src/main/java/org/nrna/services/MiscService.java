package org.nrna.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;
import org.nrna.dao.News;
import org.nrna.dao.University;
import org.nrna.dao.UniversityOutreach;
import org.nrna.dto.response.news.NewsResult;
import org.nrna.dto.response.news.SerpApi;
import org.nrna.dto.response.UniversityOutreachResponse;
import org.nrna.repository.MiscRepository;
import org.nrna.repository.UniversityOutreachRepository;
import org.nrna.repository.UniversityRepository;
import org.nrna.services.exception.CustomGenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MiscService {

    @Autowired
    MiscRepository miscRepository;

    @Autowired
    UniversityOutreachRepository universityOutreachRepository;

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
        ObjectMapper objectMapper = new ObjectMapper();
        SerpApi serpApiNews;
        try {
            serpApiNews = objectMapper.readValue(newsJSON.toString().substring(5, newsJSON.toString().length()), SerpApi.class);
        } catch (Exception ex) {
            System.out.println("Error Parsing News Result" + ex.getMessage());
            throw new CustomGenericException(ex.getMessage());
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

    public List<UniversityOutreachResponse> getListOfUniversityOutreach(){
        List<UniversityOutreach> universityOutreaches = universityOutreachRepository.findAll();
        List<UniversityOutreachResponse> universityOutreachResponses = universityOutreaches
                .stream()
                .map(universityOutreach ->
                        new UniversityOutreachResponse(
                                universityOutreach.getFullName(),
                                universityOutreach.getPhoneNumber(),
                                universityOutreach.getEmail(),
                                universityOutreach.getAssociatedUniversities(),
                                universityOutreach.getIsNSU()
                                )
                ).collect(Collectors.toList());
        return universityOutreachResponses;
    }

}
