/**
 * Design tokens for the UPI Expense Tracker.
 *
 * Palette rationale:
 * - Brand (indigo) reads as trustworthy/financial without being the generic
 *   "default blue" of the original app (#2563EB) — deepened + paired with a
 *   proper scale instead of a single hex value.
 * - Credit/Debit/Warning are first-class semantic tokens (the old app used
 *   raw `Color.Red` / `Color(0xFF2E7D32)` inline in screens — inconsistent
 *   and impossible to theme). Every screen should reference these, never a
 *   raw hex.
 * - Full light + dark scales so `userInterfaceStyle: automatic` (app.json)
 *   just works.
 */

export const palette = {
  indigo50: "#EEF2FF",
  indigo100: "#E0E7FF",
  indigo500: "#6366F1",
  indigo600: "#4F46E5",
  indigo700: "#4338CA",

  emerald50: "#ECFDF5",
  emerald500: "#10B981",
  emerald600: "#059669",

  rose50: "#FFF1F2",
  rose500: "#F43F5E",
  rose600: "#E11D48",

  amber50: "#FFFBEB",
  amber500: "#F59E0B",
  amber600: "#D97706",

  slate50: "#F8FAFC",
  slate100: "#F1F5F9",
  slate200: "#E2E8F0",
  slate400: "#94A3B8",
  slate500: "#64748B",
  slate700: "#334155",
  slate800: "#1E293B",
  slate900: "#0F172A",

  midnight900: "#0B1220",
  midnight800: "#111827",
  midnight700: "#1F2937",
} as const;

export const lightColors = {
  background: palette.slate50,
  surface: "#FFFFFF",
  surfaceAlt: palette.slate100,
  border: palette.slate200,

  textPrimary: palette.slate900,
  textSecondary: palette.slate500,
  textOnPrimary: "#FFFFFF",

  primary: palette.indigo600,
  primaryPressed: palette.indigo700,
  primaryMuted: palette.indigo50,

  credit: palette.emerald600,
  creditMuted: palette.emerald50,
  debit: palette.rose600,
  debitMuted: palette.rose50,
  warning: palette.amber600,
  warningMuted: palette.amber50,

  tabInactive: palette.slate400,
};

export const darkColors = {
  background: palette.midnight900,
  surface: palette.midnight800,
  surfaceAlt: palette.midnight700,
  border: palette.slate700,

  textPrimary: palette.slate50,
  textSecondary: palette.slate400,
  textOnPrimary: "#FFFFFF",

  primary: palette.indigo500,
  primaryPressed: palette.indigo600,
  primaryMuted: palette.midnight700,

  credit: palette.emerald500,
  creditMuted: palette.midnight700,
  debit: palette.rose500,
  debitMuted: palette.midnight700,
  warning: palette.amber500,
  warningMuted: palette.midnight700,

  tabInactive: palette.slate500,
};

export type ThemeColors = typeof lightColors;
