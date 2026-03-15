import { User } from './user';

export type BookingStatus = 'PENDING' | 'ACCEPTED' | 'REJECTED' | 'CANCELLED' | 'COMPLETED';

export interface Booking {
    id: number;
    homer: User;
    cleaner: User;
    date: string;
    startTime: string;
    endTime: string;
    surface: number;
    totalPrice: number;
    status: BookingStatus;
}

export interface BookingRequestDto {
    cleanerId: number;
    date: string;
    startTime: string;
    endTime: string;
    surface: number;
}
