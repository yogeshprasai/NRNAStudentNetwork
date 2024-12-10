import { Component, OnInit } from '@angular/core';
import { NewsService } from 'src/app/shared/service/news.service';
import {News} from "../../shared/model/news";

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private newsService: NewsService) { }

  public news: News[] = [];

  ngOnInit() {
    this.newsService.getLatestNews().subscribe((response: News[]) => {
      if(response.length){
        this.news = response;
        this.news = response.sort((a, b) =>  {
             let aDate: string = a.newsDate;
             let bDate: string = b.newsDate;
             if(a.newsDate.includes("hour") || a.newsDate.includes("hours") || b.newsDate.includes("hour") || b.newsDate.includes("hours")){
               if(a.newsDate.includes("hour") || a.newsDate.includes("hours")){
                 aDate = new Date().toString();
               }else if(b.newsDate.includes("hour") || b.newsDate.includes("hours")){
                 bDate = new Date().toString();
               }
             }
             return Date.parse(bDate) - Date.parse(aDate);
        });
      }
    });
  }

  goTo(link: string){
    window.location.href = link;
  }

}