import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token: string): void {
    window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: any): void {
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(): string | null {
    return localStorage.getItem(TOKEN); // returns null if no token
  }

  static getUser(): any | null {
    const userString = localStorage.getItem(USER);
    if (!userString) return null; // safe check
    try {
      return JSON.parse(userString);
    } catch (error) {
      console.error("Error parsing user from localStorage:", error);
      return null;
    }
  }

  static getUserRole(): string {
    const user = this.getUser();
    return user?.role || '';
  }

  static isAdminLoggedIn(): boolean {
    const role = this.getUserRole();
    return role === "ADMIN";
  }

  static isEmployeeLoggedIn(): boolean {
    const role = this.getUserRole();
    return role === "EMPLOYEE";
  }

  static getUserId(): string {
    const user = this.getUser();
    return user?.id || '';
  }

  static logout(): void {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
