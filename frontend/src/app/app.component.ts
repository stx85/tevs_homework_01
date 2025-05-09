import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DataInputComponent } from "./data-input/data-input.component";
import { DataListComponent } from "./data-list/data-list.component";

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet, 
    DataInputComponent, 
    DataListComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
