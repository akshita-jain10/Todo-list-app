import { Component, ViewChild, OnInit } from "@angular/core";
import { AdminService } from '../../services/admin.service';
import {
  ChartComponent,
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexTitleSubtitle,
  ApexYAxis
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis:ApexYAxis;
  title: ApexTitleSubtitle;
};

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrl: './statistics.component.css'
})
export class StatisticsComponent implements OnInit {
  @ViewChild("chart") chart!: ChartComponent;
  public chartOptions!: Partial<ChartOptions>;

  public taskStats = {
    pending: 0,
    inProgress: 0,
    completed: 0,
    deferred: 0,
    cancelled: 0,
    total: 0
  };

  constructor(private adminService: AdminService) {
    // Initialize chart options properly
    this.initializeChart();
  }

  initializeChart() {
    this.chartOptions = {
      series: [
        {
          name: "Tasks",
          data: [0, 0, 0, 0, 0]
        }
      ],
      chart: {
        height: 350,
        type: "bar"
      },
      title: {
        text: "Task Status Distribution"
      },
      xaxis: {
        categories: ["Pending", "In Progress", "Completed", "Deferred", "Cancelled"]
      },
      yaxis: {
      min: 0,
      max: 10,
      tickAmount: 5, // will give you ticks at 0, 5, 10
      labels: {
        formatter: (val: number) => val.toFixed(0)
      }
    }
    };
  }

  ngOnInit() {
    this.loadTaskStatistics();
  }

  loadTaskStatistics() {
    this.adminService.getAllTasks().subscribe((tasks) => {
      this.calculateStatistics(tasks);
      this.updateChart();
    });
  }

  calculateStatistics(tasks: any[]) {
    this.taskStats = {
      pending: tasks.filter(task => task.taskStatus === 'PENDING').length,
      inProgress: tasks.filter(task => task.taskStatus === 'INPROGRESS').length,
      completed: tasks.filter(task => task.taskStatus === 'COMPLETED').length,
      deferred: tasks.filter(task => task.taskStatus === 'DEFERRED').length,
      cancelled: tasks.filter(task => task.taskStatus === 'CANCELLED').length,
      total: tasks.length
    };
  }

  updateChart() {
    this.chartOptions = {
      ...this.chartOptions,
      series: [
        {
          name: "Tasks",
          data: [
            this.taskStats.pending,
            this.taskStats.inProgress,
            this.taskStats.completed,
            this.taskStats.deferred,
            this.taskStats.cancelled
          ]
        }
      ]
    };
  }

  refreshData() {
    this.loadTaskStatistics();
  }
}