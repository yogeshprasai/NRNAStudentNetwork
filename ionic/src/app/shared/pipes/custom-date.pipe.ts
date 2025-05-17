import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'customDate'
})
export class CustomDatePipe implements PipeTransform {

  transform(dbDate: any, args?: any): any {
    if(dbDate){
      const todayDateInDBFormat: string = this.formatDate(new Date().toString());
      if(dbDate.includes("minute") || dbDate.includes("minutes") || dbDate.includes("hour") || dbDate.includes("hours")){
        return "Today";
      }
      const displayDate = new Date(dbDate).toDateString();
      const lastSpaceIndex = displayDate.lastIndexOf(' ');
      return displayDate.substring(0, lastSpaceIndex) + ', ' + displayDate.substring(lastSpaceIndex + 1);
    }
    return null;
  }

  private formatDate(date: string) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
      month = '0' + month;
    if (day.length < 2)
      day = '0' + day;

    return [year, month, day].join('-');
  }

}
