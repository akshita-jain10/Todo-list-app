import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
 listOfTasks  : any=[];
 searchForm!: FormGroup;

constructor(private service: AdminService , private snackBar: MatSnackBar,private fb:FormBuilder){
  this.getTasks();
  this.searchForm = this.fb.group({
    title:[null]
  })
 }
getTasks(){
  this.service.getAllTasks().subscribe((res)=>
{  this.listOfTasks = res;

})
}
deleteTask(id:string){
  this.service.deleteTask(id).subscribe((res)=>{
    this.snackBar.open("Task deleted successfully", "close",{duration:5000});  
    this.getTasks();
  } )
}
// searchTask(){
//   const title = this.searchForm.get('title')!.value;
//   console.log(title);
  
//   // Only clear the list if search input is empty
//   if (!title || title.trim() === '') {
//     this.getTasks(); // Show all tasks when search is empty
//     return;
//   }
  
//   this.service.searchTask(title).subscribe((res)=>{
//     console.log(res);
//     this.listOfTasks = res; // Don't clear before API call, just replace with results
//   });
// }
searchTask(){
  this.listOfTasks=[];
  const title = this.searchForm.get('title')!.value  ;
  console.log(title);
  this.service.searchTask(title).subscribe((res)=>{
    console.log(res)
    this.listOfTasks=res; 
  })
}
}
