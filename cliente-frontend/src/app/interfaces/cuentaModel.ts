import { Movimiento } from "./movientoModel"

export interface Cuenta {

    id:number
    numeroCuenta:string
    fechaCreacion:string

    saldoActual:number
    movimientos:Movimiento[]
    

    
}
