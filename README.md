# UPI Expense Tracker — React Native Rewrite

Rewrite of the original native Android (Kotlin/Compose/Room) app, targeting
Android + iOS. **SMS auto-import is Android-only** — iOS has no public API
for reading SMS, so iOS gets manual entry only.

Stack: Expo (dev-client) + TypeScript + expo-sqlite + React Navigation + Zustand.

## Status: Step 1 — scaffolding ✅ (this delivery)

- [x] Project structure, deps, TS config
- [x] Theme system (light/dark, semantic colors)
- [x] Navigation shell (bottom tabs + modal stack) with placeholder screens
- [x] Shared TS types mirroring the original Room entities
- [x] TS contract for the future native SMS module (implementation = Step 3)

**Not yet done (later steps):** SQLite data layer, real screens, native SMS
module implementation, platform-conditional UI, CI.

## Running this (on your own machine — this sandbox has no Android/iOS SDKs)

```bash
npm install
npx expo prebuild
npx expo run:android   # or run:ios
```

Because we need a custom native module for SMS, this project uses
`expo-dev-client` — **not** the plain Expo Go app.

## Roadmap & confirmation gates

Per your request, each step below gets built, then confirmed with you before
moving to the next:

1. ~~Scaffolding~~ ← you are here
2. Data layer (SQLite schema + repositories)
3. Android native SMS module (Kotlin, Expo Modules API) + parser port
4. Home screen (real data)
5. Transactions screen (list/search/filter)
6. Add Transaction screen
7. Budgets screen
8. Analytics screen
9. Settings screen
10. Navigation polish
11. iOS-specific UI (hide SMS UI, manual-first flow)
12. Build & CI

## Bug-fix tracker (carried over from the Android app audit)

These will be fixed as part of the step where the relevant code lands —
tracked here so nothing gets silently dropped:

| Bug | Original cause | Fixed in |
|---|---|---|
| Import silently no-ops on first run | Permission requested async, import ran immediately without waiting for grant, then flag set permanently | Step 3 |
| Budget not scoped to a real month | `SELECT * FROM budgets LIMIT 1`, no `WHERE month=...` | Step 2 (schema) + Step 7 (screen) |
| Budgets hardcoded to `categoryId = 0` | No category picker UI | Step 7 |
| BOB SMS skips amount sanity check | Early `return` before the `amount <= 0 \|\| amount > 1,000,000` guard | Step 3 (parser port) |
| Import count can overstate actual saves | Conflict-ignored inserts still counted as "imported" | Step 3 |
| Duplicate/dead code (`Screen.kt` vs `Routes.kt`, unused `TransactionScreen.kt`/`TransactionItem.kt`, duplicate `getTodayExpense`/`getTodayDebit` queries) | Legacy leftovers | N/A — rewrite starts clean, no duplication introduced |
| No categorization UI despite schema support | Incomplete feature | Step 6/7 (category picker added to Add Transaction + Budgets) |
| Destructive migration wipes data on schema change | `fallbackToDestructiveMigration()`, no real migrations | Step 2 (versioned SQLite migrations from day one) |
| Inconsistent inline colors (`Color.Red`, `Color(0xFF2E7D32)`) | No design tokens | Fixed now — see `src/theme/colors.ts`, every screen must use theme tokens, never raw hex |

## Design system

See `src/theme/colors.ts` for the full rationale. Summary:
- **Primary (indigo)** — brand/actions
- **Credit = emerald**, **Debit = rose**, **Budget warning = amber** — consistent
  semantic tokens used everywhere money is shown, instead of ad hoc inline colors
- Full **light + dark** scales, so the app automatically matches system theme
