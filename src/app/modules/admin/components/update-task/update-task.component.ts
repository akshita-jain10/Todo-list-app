import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
export class UpdateTaskComponent {
  
  updateTaskForm!: FormGroup;
    listOfEmployees:any=[];
    listOfPriorities:any=["LOW","MEDIUM","HIGH"];
    listOfStatus:any=["PENDING","INPROGRESS","COMPLETED","DEFERRED",    "CANCELLED"];
  
  id: string = '';
  taskData: any = {};
  
  constructor(
    private snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder, 
    private adminService: AdminService,
    private service: AdminService,
    private route: ActivatedRoute
  ) {
    // Initialize id from route parameters after constructor injection
    this.id = this.route.snapshot.params["id"];
    this.getTaskById();
    this.getUsers();
    this.updateTaskForm = this.fb.group({ 
        employeeId:[null,[Validators.required]],
        title:[null,[Validators.required]],
        description:[null,[Validators.required]],
        dueDate:[null,[Validators.required]],
        priority:[null,[Validators.required]],
        taskStatus:[null,[Validators.required]],
    
  })
}

  getTaskById() {
    this.service.getTaskById(this.id).subscribe((res) => {
      console.log('Task data:', res);
      this.taskData = res;
      this.updateTaskForm.patchValue(res);
    }, (error) => {
      console.error('Error fetching task:', error);
    });
  }
  getUsers(){
  this.adminService.getUsers().subscribe((res)=>{
    this.listOfEmployees = res;
    console.log(res);
  })
}
updateTask(){
  console.log( this.updateTaskForm.value)
this.adminService.updateTask(this.id,this.updateTaskForm.value).subscribe((res)=>{
    if(res.id !=null){
this.snackBar.open("Task updated successfully!","close",{duration : 5000});
    this.router.navigateByUrl("/admin/dashboard");
    }else{
this.snackBar.open("Something went wrong","ERROR",{duration : 5000});

    }
  })
}
}