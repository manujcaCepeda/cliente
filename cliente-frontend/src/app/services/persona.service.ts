import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Cliente } from '../interfaces/clienteModel';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {

  baseUrl = `http://localhost:8080/clientes`

  constructor(private http:HttpClient) { }




  obtenerPersona(cedula:string){
    return this.http.get<Cliente>(`${this.baseUrl}/cedula/${cedula}`);
  }


}
