import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PointRequest} from "../../../model/PointRequest";
import {PointService} from "../../../services/point.service";
import {PointResponse} from "../../../model/PointResponse";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-main-form',
  templateUrl: './main-form.component.html',
  styleUrls: ['./main-form.component.css']
})
export class MainFormComponent implements OnInit {
  public pointForm = new FormGroup({
    x: new FormControl("", Validators.required),
    y: new FormControl("", Validators.required),
    r: new FormControl("", Validators.required)
  });

  private readonly DISPLAY_NONE: string = "none";
  private readonly DISPLAY_BLOCK: string = "block";
  display_x: string = this.DISPLAY_NONE;
  display_y_null: string = this.DISPLAY_NONE;
  display_y_invalid: string = this.DISPLAY_NONE;
  display_r: string = this.DISPLAY_NONE;

  readonly x_values: number[] = [-5, -3, -2, -1, 0, 1, 2, 3];
  readonly Y_MIN_VALUE: number = -5;
  readonly Y_MAX_VALUE: number = 5;
  readonly r_values: number[] = [1, 2, 3, 4, 5];

  @Output() rChangeEvent = new EventEmitter<number>()
  @Output() newPointEvent = new EventEmitter<PointResponse>();
  @Output() deletePointsEvent = new EventEmitter<any>()
  constructor(private authService: AuthService,
              private pointService: PointService,
              private router: Router) { }

  ngOnInit(): void {
  }

  submit():void{
    if(this.validateData()){
      let point: PointRequest = new PointRequest(
        Number(this.pointForm.value.x),
        Number(this.pointForm.value.y),
        Number(this.pointForm.value.r)
      );
      this.pointService.postPoint(point).subscribe({
        next: (data:PointResponse) => {
          this.newPointEvent.emit(data);
        },
        error: (err) => {
          console.log(err);
        }
      });
    }
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  deletePoints(){
    this.pointService.deletePoints();
    this.deletePointsEvent.emit();
  }
  private validateData():boolean{
    let isValidX = this.validateX();
    let isValidY = this.validateY();
    let isValidR = this.validateR();
    return isValidX && isValidY && isValidR;
  }
  private validateX():boolean{
    this.display_x = this.DISPLAY_NONE;
    if (this.pointForm.value.x == null || this.pointForm.value.x === "") {
      this.display_x = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }


  private validateR():boolean{
    this.display_r = this.DISPLAY_NONE;
    if (this.pointForm.value.r == null || this.pointForm.value.r === "") {
      this.display_r = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }

  private validateY():boolean{
    this.display_y_null = this.DISPLAY_NONE;
    this.display_y_invalid = this.DISPLAY_NONE;
    if (this.pointForm.value.y == null || this.pointForm.value.y === "") {
      this.display_y_null = this.DISPLAY_BLOCK;
      return false;
    }
    let y_value = Number(this.pointForm.value.y);
    if(isNaN(y_value)){
      this.display_y_invalid = this.DISPLAY_BLOCK;
      return false;
    }
    if((y_value <= this.Y_MIN_VALUE) || (y_value >= this.Y_MAX_VALUE)){
      this.display_y_invalid = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }

  rChange(){
    let r:number = Number(this.pointForm.value.r);
    this.rChangeEvent.emit(r);

  }
}
