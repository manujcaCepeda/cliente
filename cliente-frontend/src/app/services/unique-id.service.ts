import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class UniqueIdService {
  private secretKey = 'mySecretKey12345'; // Llave secreta compartida con el backend

  constructor() { }

  generateUniqueIdentifier2(): string {
    // Generar UUID versión 4
    const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      const r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });

    // Añadir la marca de tiempo
    const timestamp = new Date().getTime().toString();
    const uniqueId = `${uuid}-${timestamp}`;

    console.log("----------------------------> " + uniqueId)

    // Encriptar el ID único
    const encryptedId = CryptoJS.AES.encrypt(uniqueId, this.secretKey).toString();

    return encryptedId;
  }

  generateUniqueIdentifier(): string {
    this.ejemplo();
    const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      const r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });

    const timestamp = new Date().getTime().toString();
    const uniqueId = `${uuid}-${timestamp}`;
    console.log("uniqueId ------------------> " + uniqueId)

    // Encriptar y codificar en base64
    const encryptedId = this.encrypt(uniqueId, this.secretKey).toString();

    console.log("encryptedId ------------------> " + encryptedId)

    return encryptedId;
  }

  ejemplo() {
    const secretKey = 'mySecretKey12345';
    const text = 'Hola Mundo';

    // Encriptar el texto
    const encryptedText = this.encrypt(text, secretKey).toString();
    console.log('Encrypted Text:', encryptedText);

    // Decriptar
    const decryptedText = this.decrypt(encryptedText, secretKey);
    console.log('Decrypted:', decryptedText);
    }





    encrypt1(text: string, secretKey: string): string {
        const key = CryptoJS.enc.Utf8.parse(secretKey);
        const iv = CryptoJS.enc.Utf8.parse('1234567890123456');  // IV de 16 bytes, por ejemplo
        const encrypted = CryptoJS.AES.encrypt(text, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return encrypted.toString();
    }
    
    decrypt1(encryptedText: string, secretKey: string): string {
        const key = CryptoJS.enc.Utf8.parse(secretKey);
        const iv = CryptoJS.enc.Utf8.parse('1234567890123456');  // IV de 16 bytes, debe coincidir con el cifrado
        const decrypted = CryptoJS.AES.decrypt(encryptedText, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        return decrypted.toString(CryptoJS.enc.Utf8);
    }




    encrypt(text: string, secretKey: string): string {
        const key = CryptoJS.enc.Utf8.parse(secretKey);
        const iv = CryptoJS.lib.WordArray.random(16); // Genera un IV aleatorio de 16 bytes
        const encrypted = CryptoJS.AES.encrypt(text, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        // Retorna el IV junto con el texto cifrado, codificados en Base64
        return iv.toString(CryptoJS.enc.Base64) + ':' + encrypted.toString();
    }
    
    decrypt(encryptedText: string, secretKey: string): string {
        const key = CryptoJS.enc.Utf8.parse(secretKey);
        const encryptedArray = encryptedText.split(':');
        const iv = CryptoJS.enc.Base64.parse(encryptedArray[0]); // Extrae el IV del texto cifrado
        const encrypted = encryptedArray[1];
    
        const decrypted = CryptoJS.AES.decrypt(encrypted, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    
        return decrypted.toString(CryptoJS.enc.Utf8);
    }
}
