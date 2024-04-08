import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../../services/auth.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm = new FormGroup({
    username: new FormControl("", Validators.required),
    password: new FormControl("", Validators.required),
    confirmPassword: new FormControl("", Validators.required)
  });

  private readonly DISPLAY_NONE: string = "none";
  private readonly DISPLAY_BLOCK: string = "block";

  display_username_null: string = this.DISPLAY_NONE;
  display_username_invalid: string = this.DISPLAY_NONE;
  display_password_null: string = this.DISPLAY_NONE;
  display_password_invalid: string = this.DISPLAY_NONE;
  display_confirm_password_null: string = this.DISPLAY_NONE;
  display_confirm_password_invalid: string = this.DISPLAY_NONE;
  display_server_message: string = this.DISPLAY_NONE;

  server_message: string = "";
  constructor(private authService: AuthService,
              private cookieService: CookieService,
              private router: Router) { }

  ngOnInit(): void {
  }

  register(): void{
    this.display_server_message = this.DISPLAY_NONE;
    if(this.validateCredentials()){
      this.authService.register(this.registerForm.value.username, this.registerForm.value.password).subscribe({
        next: response => {
          this.cookieService.set("token", response.body.token);
          this.router.navigate(['/main']);
        },
        error: err => {
          if (err.status == 403) {
            this.showServerMessage(err.error);
          } else {
            this.showServerMessage("Произошла ошибка, повторите попытку позднее.");
          }
        }
      });
    }
  }

  private validateCredentials():boolean{
    let isCorrectUsername: boolean = this.validateUsername();
    let isCorrectPassword: boolean = this.validatePassword();
    let isPasswordConfirmed = this.validateConfirmPassword();
    return isCorrectUsername && isCorrectPassword && isPasswordConfirmed;
  }
  private validatePassword(): boolean{
    this.display_password_invalid = this.DISPLAY_NONE;
    this.display_password_null = this.DISPLAY_NONE;
    let password = this.registerForm.value.password;
    if(password == null || password == ""){
      this.display_password_null = this.DISPLAY_BLOCK;
      return false;
    }
    if(!password.match("^(?=.*?[0-9])(?=.*?[A-Za-z]).{6,32}$")){
      this.display_password_invalid = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }

  private validateUsername(): boolean{
    this.display_username_null = this.DISPLAY_NONE;
    this.display_username_invalid = this.DISPLAY_NONE;
    let username = this.registerForm.value.username;
    if(username == null || username == ""){
      this.display_username_null = this.DISPLAY_BLOCK;
      return false;
    }
    if(!username.match("^[0-9A-Za-z]{4,20}$")){
      this.display_username_invalid = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }

  private validateConfirmPassword(): boolean{
    this.display_confirm_password_null = this.DISPLAY_NONE;
    this.display_confirm_password_invalid = this.DISPLAY_NONE;
    let confirmPassword = this.registerForm.value.confirmPassword;
    let password = this.registerForm.value.password;
    if(confirmPassword == null || confirmPassword == ""){
      this.display_confirm_password_null = this.DISPLAY_BLOCK;
      return false;
    }
    if(confirmPassword != password){
      this.display_confirm_password_invalid = this.DISPLAY_BLOCK;
      return false;
    }
    return true;
  }
  private showServerMessage(message: string): void{
    this.server_message = message;
    this.display_server_message = this.DISPLAY_BLOCK;
  }

}
