import { User } from './user';

export interface AuthResponse {
    token?: string; // If using JWT later
    user: User;
}
