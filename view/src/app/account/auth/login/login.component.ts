import { Router } from '@angular/router';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { MessageService } from 'src/app/shared/message/message.service';
import { AuthClientService } from 'src/app/core/client/auth-client/auth-client.service';
import { SecurityService } from 'src/app/core/security/security.service';
import { User } from 'src/app/core/security/credential';

/**
 * Login component.
 * 
 * @author Manoel Veríssimo
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, AfterViewInit {

  public loginForm: FormGroup;

  public submitted: boolean = false;

  /**
   * Constructor classe
   * 
   * @param router 
   * @param formBuilder 
   * @param messageService 
   * @param securityService 
   * @param authClientService 
   */
  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private securityService: SecurityService,
    private authClientService: AuthClientService
  ) { }

  /**
   * Init component.
   */
  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      login: ['', [Validators.required]],
      password: ['', Validators.required],
    });
  }

  /**
   * Init post construct view.
   */
  ngAfterViewInit() {
    document.body.classList.add('authentication-bg');
  }

  /**
   * On submit form
   */
  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    const authTO: any = {
      login: this.loginControls.login.value,
      password: this.loginControls.password.value
    }

    this.authClientService.login(authTO).subscribe((data: any) => {
      const user: User = {
        id: data.userId,
        name: data.name,
        login: data.login,
        roles: data.roles,
        accessToken: data.accessToken,
        expiresIn: data.accessExpiresIn,
        refreshToken: data.refreshToken
      }
      this.securityService.init(user);
      this.router.navigate(['']);
    }, error => {
      this.messageService.addMsgDanger(error);
    });
  }

  /**
   * @returns the loginControls
   */
  get loginControls() {
    return this.loginForm.controls;
  }
}
