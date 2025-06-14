import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BackendService } from '../shared/backend.service';
import { Status } from '../shared/interfaces/status';

@Component({
  selector: 'app-data-input',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
  ],
  templateUrl: './data-input.component.html',
  styleUrl: './data-input.component.scss'
})
export class DataInputComponent implements OnInit {
  public formGroup!: FormGroup;
  public isPositiveResponse: number;

  constructor(
    public formBuilder: FormBuilder,
    private backend: BackendService
  ) {
    this.isPositiveResponse = 0;
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      name: ['', [Validators.required]],
      info: ['', [Validators.required]]
    });
  }

  reset() {
    this.formGroup.reset({
      name: '',
      info: ''
    })

    Object.values(this.formGroup.controls).forEach(control => {
      control.markAsPristine();
      control.markAsUntouched();
    })

    this.isPositiveResponse = 0;
  }

  onSubmit() {
    if (this.formGroup.valid) {
      let status: Status = {
        name: this.formGroup.value['name'],
        info: this.formGroup.value['info'],
        date: new Date()
      };

      this.backend.sendStatus(status).subscribe(result => {
        if (result) {
          this.reset();
          this.isPositiveResponse = 1;
        } else {
          this.reset();
          this.isPositiveResponse = -1;
        }
      });
    }
  }
}
