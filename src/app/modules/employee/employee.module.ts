import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmployeeRoutingModule } from './employee-routing.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DemoAngularMaterialModule } from "../../DemoAngularMaterialModule";


@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    EmployeeRoutingModule,
    DemoAngularMaterialModule
]
})
export class EmployeeModule { }
