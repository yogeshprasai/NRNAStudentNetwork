import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import { NewsService } from 'src/app/shared/service/news.service';
import {News} from "../../shared/model/news";
import {Router} from "@angular/router";

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {

  @ViewChild('newsSource') newsSource: any = null;
  constructor(private newsService: NewsService, private renderer: Renderer2, private router: Router) { }

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

  ngAfterViewInit(){
    //this.renderer.setStyle(this.newsSource.nativeElement, 'color', 'red');
  }


  changeColor(){
    //return "linear-gradient(yellow, red)";
    const firstEnumArray = Object.values(firstGradient);
    const secondEnumArray = Object.values(secondGradient);
    console.log(firstEnumArray);
    const randomNumber1 = Math.floor(Math.random()* firstEnumArray.length);
    const randomNumber2 = Math.floor(Math.random()* firstEnumArray.length);
    return "linear-gradient("+ firstEnumArray[randomNumber1] +", "+ secondEnumArray[randomNumber2]  + ")";
  }

  navigateButton(){
    this.router.navigateByUrl('/about-us');
  }


}

export const firstGradient = {
  ACDDDE : "#d6e3e0",
  CAF1DE : "#eae5e4",
  E1F8DC : "#e2e6e1",
  FEF8DD : "#dad9d5",
  FFE7C7 : "#e6e7ec",
  F7D8BA : "#e4ded9",
  E7D0BA : "#e1d7de",
  F7E8BA : "#e8e6df",
  F7D2BA : "#e8e4e2",
  F7D8BE : "#eaefea"
}

export const secondGradient = {
  ACDDDE : "#a4b9ba",
  CAF1DE : "#87aa99",
  E1F8DC : "#aab3a8",
  FEF8DD : "#938f7b",
  FFE7C7 : "#a59988",
  F7D8BA : "#828893",
  E7D0BA : "#9e9ba6",
  F7E8BA : "#a598a5",
  F7D2BA : "#bda692",
  F7D8BE : "#82808e"
}