import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Status } from './interfaces/status';
import { catchError, Observable, map, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  private apiUrl = 'http://localhost:8080/status'

  constructor(private http: HttpClient) { }

  public sendStatus(data: Status): Observable<boolean> {
    const url = `${this.apiUrl}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json'});

    console.log(data);

    return this.http.post<Status>(url, data, { headers }).pipe(
      map(() => true),
      catchError(error => {
        console.error('Error: ', error);
        return of(false);
      })
    );
  }

  public requestAllStatus(): Observable<Status[]> {
    return this.http.get<Status[]>(`${this.apiUrl}/all`);
  }

  public requestStatus(name: string): Observable<Status> {
    return this.http.get<Status>(`${this.apiUrl}?name=${name}`);
  }
}
