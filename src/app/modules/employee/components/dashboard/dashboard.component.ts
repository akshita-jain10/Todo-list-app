import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  listOfTask: any = []
  constructor(private service:EmployeeService,
    private snackBar: MatSnackBar 
  ){
    this.getTasks();
  }

  getTasks(){
    this.service.getEmployeeTasksById().subscribe((res)=>{
      console.log(res);
      this.listOfTask = res;
    })
  }
updateStatus(id:string,status:string){
  this.service.updateStatus(id,status).subscribe((res)=>{
    if(res.id!=null){
      this.snackBar.open("Task Updated Successfully","Close",{duration:5000});
      this.getTasks(); 
    }else{
            this.snackBar.open("Getting error while updating task","Close",{duration:5000});

    }
  })
}
}