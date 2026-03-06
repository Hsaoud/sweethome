export interface User {
    id: number;
    email: string;
    role: 'HOMER' | 'CLEANER' | 'ADMIN';
    firstName: string;
    lastName: string;
    // Common fields
    phone?: string;
    city?: string;
    latitude?: number;
    longitude?: number;
    // Cleaner specific
    headline?: string;
    bio?: string;
    pricePerSqm?: number;
    actionRadiusKm?: number;
    experienceYears?: number;
    available?: boolean;
}
