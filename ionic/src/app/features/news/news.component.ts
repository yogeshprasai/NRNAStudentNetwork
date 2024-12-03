import { Component, OnInit } from '@angular/core';
import { NewsService } from 'src/app/shared/service/news.service';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  constructor(private newsService: NewsService) { }

  ngOnInit() {
    this.newsService.getLatestNews().subscribe(response => {
      console.log(response);
    });
  }

}
