import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CleanerService } from '../../../services/cleaner/cleaner.service';
import { User } from '../../../models/user';

@Component({
  selector: 'app-cleaner-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cleaner-list.component.html',
  styleUrl: './cleaner-list.component.scss'
})
export class CleanerListComponent implements OnInit {
  cleaners: User[] = [];
  searchCity = '';

  cleanerService = inject(CleanerService);

  ngOnInit() {
    this.loadCleaners();
  }

  loadCleaners() {
    this.cleanerService.getCleaners(this.searchCity).subscribe(data => {
      this.cleaners = data;
    });
  }

  onSearch() {
    this.loadCleaners();
  }
}
