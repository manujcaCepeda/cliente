import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componets/login/login.component';
import { PurchaseComponent } from './componets/purchase/purchase.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'purchases', component: PurchaseComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
