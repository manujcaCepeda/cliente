import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  private apiUrl = 'http://localhost:8080/compras';

  constructor(private http: HttpClient) { }

  addPurchase(purchase: any): Observable<any> {
    return this.http.post(this.apiUrl, purchase, {
      headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
    });
  }

  getPurchases(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, {
      headers: { 'Authorization': `Bearer ${localStorage.getItem('token')}` }
    });
  }
}
