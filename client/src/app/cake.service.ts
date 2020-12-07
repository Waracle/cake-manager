import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Cake } from './cake';
import {MessageService} from './message.service';


@Injectable({
  providedIn: 'root'
})
export class CakeService {

private baseUrl = 'http://localhost:8282/api/';
private displayCakesUrl = this.baseUrl;
private cakesUrl = this.baseUrl + 'cakes';
private cakeDetailUrl = this.baseUrl + 'cakeDetail';

  httpOptions = {
    headers : new HttpHeaders({'Content-Type':'application/json'})
  };

  constructor(private http: HttpClient, private messageService: MessageService) { }

    getSavedCakes(): Observable<Cake[]>{
      this.messageService.add('CakeService: fetched cakes');
      return this.http.get<Cake[]>(this.displayCakesUrl)
      .pipe(
      tap(_ => this.log('fetched saved cakes')),
        catchError(this.handleError<Cake[]>('getCakes', []))
        );
    }

  getCakes(): Observable<Cake[]>{
    this.messageService.add('CakeService: fetched cakes');
    return this.http.get<Cake[]>(this.cakesUrl)
    .pipe(
    tap(_ => this.log('fetched cakes')),
      catchError(this.handleError<Cake[]>('getCakes', []))
      );
  }

  addCake(cake: Cake): Observable<Cake>{
  this.messageService.add('CakeService: add cake');
    return this.http.post<Cake>(this.cakesUrl, cake, this.httpOptions).pipe(
    tap((newCake: Cake) => this.log('added cake id=${newCake.id}')),
    catchError(this.handleError<Cake>('addCake'))
    );
  }

  getCake(id: number): Observable<Cake>{
    const url = `${this.cakeDetailUrl}/${id}`;
            return this.http.get<Cake>(url).pipe(
              tap(_ => this.log(`fetched cake id=${id}`)),
              catchError(this.handleError<Cake>(`getCakes id=${id}`))
            );
  }

    /** Log a CakeService message with the MessageService**/
    private log(message: string){
      this.messageService.add(`CakeService: ${message}`);
    }

    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    private handleError<T> (operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {

        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead

        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`);

        // Let the app keep running by returning an empty result.
        return of(result as T);
      };
    }

}
