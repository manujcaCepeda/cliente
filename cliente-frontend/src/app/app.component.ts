import { Component } from '@angular/core';
import { Cliente } from './interfaces/clienteModel';
import { FormControl, FormGroup, UntypedFormControl, Validators } from '@angular/forms';
import { PersonaService } from './services/persona.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Movimiento } from './interfaces/movientoModel';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  
  cliente!: Cliente;

  displayedColumns: string[] = ['tipo', 'valor', 'detalle'];

  activarBtn = false
  clickBtn = false

  form: FormGroup;

  constructor(
    private personaService:PersonaService, readonly snackBar: MatSnackBar)
  {
    this.form = new FormGroup({
      cedula: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]{1,9}$')  // Asegura que solo se ingresen números y hasta 9 dígitos
      ])
    });
  }


  isCedulaValid(): boolean {
    const cedulaControl = this.form.get('cedula');
    // Usa paréntesis para asegurarse de que las operaciones se realicen en el orden correcto
    return (cedulaControl?.valid ?? false) && (cedulaControl?.value.length === 9);
  }


  applyFilter(event: any) {
    if (event.key === 'Tab' || event.key === 'Enter' || event.keyCode == 9) {
      this.buscarPorCedula()
    }
  }


  buscarPorCedula(){

    if (this.form.valid) {

      this.clickBtn = true;
      
      let ruc = this.form.get('cedula')!.value;

      if(ruc.length == 9){
        this.activarBtn = true
        console.log("--------------------->")

        this.personaService.obtenerPersona(ruc).subscribe({
          next:(persona => {
            if(persona){
              debugger;
              this.cliente = persona
              console.log(this.cliente)

            }else {
              this.snackBar.open("Alerta",'Cliente no mantiene una cuenta activa!',{
                verticalPosition: 'top',
                duration:3000
              })
              
            }
          }),
          error:(err)=> {
            console.log(err)
            this.snackBar.open("Alerta",'Cliente no mantiene una cuenta activa!',{
              verticalPosition: 'top',
              duration:3000
            })
          }
        })
      }else {
        console.error('Formulario inválido.');
      }
    } else {
      console.error('Formulario inválido.');
    }

  }

  // Calcula el saldo basado en los movimientos
  calcularSaldo(movimientos: Movimiento[]): number {
    return movimientos.reduce((saldo, movimiento) => {
      return movimiento.tipo === 'Credito'
        ? saldo + movimiento.valor
        : saldo - movimiento.valor;
    }, 0);
  }

}
