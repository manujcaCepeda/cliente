import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PurchaseService } from '../../services/purchase.service';
import * as CryptoJS from 'crypto-js';
import { UniqueIdService } from 'src/app/services/unique-id.service';


@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.scss']
})
export class PurchaseComponent implements OnInit {
  purchaseForm: FormGroup;
  purchases: any[] = [];
  errorMessage: string | null = null;

  // Clave secreta compartida entre frontend y backend
  SECRET_KEY = 'clave-secreta-compartida';

  constructor(
    private fb: FormBuilder,
    private purchaseService: PurchaseService,
    private uniqueIdService: UniqueIdService
  ) {
    this.purchaseForm = this.fb.group({
      nroCompra: ['', Validators.required],
      descripcion: ['', Validators.required],
      precioTotal: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.loadPurchases();
  }


  generateUniqueIdentifier(): string {
    // Generar UUID de versión 4
    const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });

    // Obtener la marca de tiempo actual en milisegundos
    const timestamp = Date.now();

    // Concatenar UUID y marca de tiempo para obtener un identificador único
    return `${uuid}-${timestamp}`;
  }

  // Encriptar el identificador único
  encryptIdentifier(identifier: string): string {
    return CryptoJS.AES.encrypt(identifier, this.SECRET_KEY).toString();
  }

  onSubmit(): void {
    if (this.purchaseForm.valid) {

      // Uso en el servicio de Angular
      const uniqueId = this.uniqueIdService.generateUniqueIdentifier();

      let compra = this.purchaseForm.value;
      compra.idempotencyKey = uniqueId
      compra.sessionToken = localStorage.getItem('sessionToken')


      this.purchaseService.addPurchase(this.purchaseForm.value).subscribe({
        next: () => {
          this.loadPurchases();  // Recargar la lista de compras después de añadir una
          this.purchaseForm.reset();
        },
        error: err => this.errorMessage = 'Failed to add purchase'
      });
    }
  }

  loadPurchases(): void {
    this.purchaseService.getPurchases().subscribe({
      next: purchases => this.purchases = purchases,
      error: err => this.errorMessage = 'Failed to load purchases'
    });
  }
}
