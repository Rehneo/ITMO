import { Component, OnInit } from '@angular/core';
import {PointService} from "../../../services/point.service";
import {PointResponse} from "../../../model/PointResponse";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-table',
  templateUrl: './main-table.component.html',
  styleUrls: ['./main-table.component.css']
})
export class MainTableComponent implements OnInit {
  headers = ["x", "y", "r", "result"];
  rows: any[] = [];

  constructor(private router: Router, private pointService: PointService) {

  }

  ngOnInit(): void {
    this.pointService.getPoints().subscribe({
      next: data => {
        this.rows = data;
      },
      error: err => {
        if (err.status == 403) {
          this.router.navigate(['/login']);
        } else {
          console.log(err);
        }
      }
    });
  }

  addPoint(point:PointResponse) {
    this.rows.push(point);
  }

  deleteRows(){
    this.rows = [];
  }
}
