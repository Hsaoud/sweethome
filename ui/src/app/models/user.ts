export interface User {
    id: number;
    email: string;
    role: 'HOMER' | 'CLEANER' | 'ADMIN';
    firstName: string;
    lastName: string;
    // Common fields
    phone?: string;
    city?: string;
    // Cleaner specific
    headline?: string;
    bio?: string;
    hourlyRate?: number;
    experienceYears?: number;
    available?: boolean;
}
