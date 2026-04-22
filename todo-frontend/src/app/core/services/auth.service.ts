import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { AuthRequest, AuthResponse } from "../../models/auth.model";
import { Observable, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private authUrl = `${environment.authUrl}/auth`;

    constructor(private http: HttpClient) {}

    register(data: AuthRequest): Observable<any> {
        return this.http.post(`${this.authUrl}/register`, data);
    }

    login(data: AuthRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${this.authUrl}/login`, data).pipe(
            tap(response => this.saveToken(response.token))
        );
    }

    logout(): void {
        localStorage.removeItem('token');
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('token');
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    saveToken(token: string): void {
        localStorage.setItem('token', token);
    }
}