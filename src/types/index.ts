/**
 * These mirror the Kotlin Room entities 1:1 so the SQLite schema (Step 2)
 * and the SMS parser port (Step 3) have a stable contract to target.
 */

export type TransactionType = "DEBIT" | "CREDIT";

export interface Transaction {
  id: number;
  amount: number;
  merchant: string | null;
  categoryId: number | null;
  transactionType: TransactionType;
  transactionDate: number; // epoch millis
  bankName: string | null;
  upiApp: string | null;
  referenceNumber: string | null;
  smsBody: string | null;
  smsAddress: string | null;
  createdAt: number;
}

export interface Category {
  id: number;
  name: string;
  icon: string | null;
  color: string | null;
  isDefault: boolean;
}

export interface Budget {
  id: number;
  categoryId: number | null; // nullable here (fixes original hardcoded categoryId=0 bug)
  monthlyLimit: number;
  alertPercentage: number;
  /** ISO "YYYY-MM" — real month scoping (fixes original getCurrentBudget() bug) */
  month: string;
}

export interface AppSettings {
  id: 1;
  currency: string;
  darkMode: boolean;
  smsPermissionGranted: boolean;
  notificationsEnabled: boolean;
}

export type TransactionFilter =
  | "ALL"
  | "TODAY"
  | "YESTERDAY"
  | "LAST_7_DAYS"
  | "LAST_30_DAYS"
  | "LAST_90_DAYS"
  | "DEBIT"
  | "CREDIT";
