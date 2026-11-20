import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  a: number = 0;
  b: number = 0;
  operation: string = 'add';
  result: number | null = null;
  error: string | null = null;
  loading = false;

  private apiUrl = '/api/calculate';

  constructor(private http: HttpClient) {}

  calculate() {
    this.loading = true;
    this.result = null;
    this.error = null;

    const url = `${this.apiUrl}/${this.operation}?a=${this.a}&b=${this.b}`;

    this.http.get<any>(url).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.error) {
          this.error = res.error;
        } else {
          this.result = res.result;
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Could not reach the API: ' + err.message;
      }
    });
  }
}
