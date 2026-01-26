import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterHomerComponent } from './pages/auth/register-homer/register-homer.component';
import { RegisterCleanerComponent } from './pages/auth/register-cleaner/register-cleaner.component';
import { CleanerListComponent } from './pages/cleaners/cleaner-list/cleaner-list.component';
import { CleanerDetailComponent } from './pages/cleaners/cleaner-detail/cleaner-detail.component';
import { CleanerDashboardComponent } from './pages/dashboard/cleaner-dashboard/cleaner-dashboard.component';
import { HomerDashboardComponent } from './pages/dashboard/homer-dashboard/homer-dashboard.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register/homer', component: RegisterHomerComponent },
    { path: 'register/cleaner', component: RegisterCleanerComponent },
    { path: 'cleaners', component: CleanerListComponent },
    { path: 'cleaners/:id', component: CleanerDetailComponent },
    { path: 'dashboard/cleaner', component: CleanerDashboardComponent }, // Add AuthGuard later
    { path: 'dashboard/homer', component: HomerDashboardComponent },   // Add AuthGuard later
    { path: '**', redirectTo: '' }
];
