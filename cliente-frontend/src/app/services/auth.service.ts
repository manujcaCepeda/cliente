import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth/login';
  private tokenSubject = new BehaviorSubject<string | null>(null);
  public token$: Observable<string | null> = this.tokenSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<void> {
    return this.http.post<{ token: string, sessionToken:string }>(this.apiUrl, { username, password })
      .pipe(
        map(response => {
          if (response.token) {
            this.tokenSubject.next(response.token);
            localStorage.setItem('token', response.token);
            localStorage.setItem('sessionToken', response.sessionToken);
          } else {
            throw new Error('Invalid response format');
          }
        }),
        catchError((error: HttpErrorResponse) => {
          console.error('Login failed', error);
          return throwError(() => new Error('Login failed'));
        })
      );
  }

  logout(): void {
    this.tokenSubject.next(null);
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
