import { ChangeDetectorRef, Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html'
})
export class RegisterComponent {

  name = '';
  email = '';
  password = '';
  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  register(): void {
    this.authService.register({ name: this.name, email: this.email, password: this.password })
      .subscribe({
        next: () => {
          this.successMessage = 'Usuário criado com sucesso!';
          this.cdr.detectChanges();
          setTimeout(() => this.router.navigate(['/login']), 2000);
        },
        error: () => {
          this.errorMessage = 'Erro ao criar usuário. Tente novamente.';
          this.cdr.detectChanges();
        }
      });
  }
}