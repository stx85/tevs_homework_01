import { Component, OnInit } from '@angular/core';
import { BackendService } from '../shared/backend.service';
import { Status } from '../shared/interfaces/status';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-data-list',
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule
  ],
  templateUrl: './data-list.component.html',
  styleUrl: './data-list.component.scss'
})
export class DataListComponent implements OnInit {
  public displayedColumns: string[] = ['name', 'info', 'date'];
  public status!: Status[];

  constructor(private backend: BackendService) {}

  ngOnInit(): void {
      this.onRequest();
  }

  onRequest() {
    this.backend.requestAllStatus().subscribe(status => {
      console.log(status);
      this.status = status;
      console.log(this.status);
    })
  }
}
