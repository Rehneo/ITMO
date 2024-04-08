import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {PointResponse} from "../../../model/PointResponse";
import {PointService} from "../../../services/point.service";
import {PointRequest} from "../../../model/PointRequest";

@Component({
  selector: 'app-main-graph',
  templateUrl: './main-graph.component.html',
  styleUrls: ['./main-graph.component.css']
})
export class MainGraphComponent implements OnInit {

  side:number = 0;
  r: number = -1;
  rectangle: string = "";
  circle: string = "";

  points: PointResponse[] = [];

  @Output() newPointEvent = new EventEmitter<PointResponse>();
  constructor(private pointService: PointService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.side = document.getElementById("svg").clientWidth;
    this.rectangle = this.side*0.3 + "," + this.side/2 + " " + this.side/2 + "," + this.side/2 + " " + this.side/2 + "," + this.side*0.9;
    this.circle = "M"+this.side*0.1 + " " + this.side/2 + " L" + this.side/2 + " " + this.side/2 + " L" + this.side/2 + " " + this.side*0.1
    + " A" + this.side*0.4 + " " + this.side*0.4 + " 0 0 0 " + this.side*0.1 + " " + this.side/2 + " Z";
    this.pointService.getPoints().subscribe(data => {
      this.points = data;
    });
  }

  rChange(r: number){
    this.r = r;
  }

  addPoint(point:PointResponse) {
    this.points.push(point);
  }

  setPoint(event: MouseEvent){
    if(this.r == -1){
      alert("Пожалуйста, выберите значение R.")
      return;
    }
    let testX = (event.offsetX - this.side/2)/(this.side*0.4)*this.r;
    let testY = (-1)*(event.offsetY - this.side/2)/(this.side*0.4)*this.r;
    if(testY <= -5 || testY >= 5) {
      alert("Значение Y должно быть в интервале (-5;5)!");
      return;
    }
    if(testX <-5 || testX > 3){
      alert("Значение X должно быть в интервале [-5,3]!");
      return;
    }
    let x = this.roundToThousandths(testX);
    let y = this.roundToThousandths(testY);
    let point: PointRequest = new PointRequest(x,y,this.r);
    this.pointService.postPoint(point).subscribe({
      next: (data:PointResponse) => {
        this.points.push(data);
        this.newPointEvent.emit(data);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  deletePoints() {
    this.points = [];
  }

  roundToThousandths(arg: number): number{
    let str = String(arg);
    if(str.length > 6){
      str = str.slice(0, 6);
    }
    return Number(str);
  }
}

