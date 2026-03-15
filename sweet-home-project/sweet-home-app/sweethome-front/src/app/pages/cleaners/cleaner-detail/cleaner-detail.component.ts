import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CleanerService } from '../../../services/cleaner/cleaner.service';

@Component({
  selector: 'app-cleaner-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cleaner-detail.component.html',
  styleUrl: './cleaner-detail.component.scss'
})
export class CleanerDetailComponent implements OnInit {
  cleanerData: any = null;
  loading = true;
  error = '';

  private route = inject(ActivatedRoute);
  private cleanerService = inject(CleanerService);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.cleanerService.getCleanerProfile(+id).subscribe({
        next: (data) => {
          this.cleanerData = data;
          this.loading = false;
        },
        error: () => {
          this.error = 'Failed to load cleaner profile';
          this.loading = false;
        }
      });
    }
  }

  getStars(rating: number): number[] {
    return Array(5).fill(0).map((x, i) => i < Math.round(rating) ? 1 : 0);
  }
}
