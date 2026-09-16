import { Platform } from "react-native";

/**
 * Contract for the Android-only native SMS module (built in Step 3 as an
 * Expo Module written in Kotlin). On iOS, none of this can exist — there is
 * no public API for reading SMS on iOS, by Apple's design. Every call here
 * must be guarded with `Platform.OS === "android"` at the call site.
 */
export interface RawSms {
  sender: string | null;
  body: string;
  timestampMillis: number;
}

export interface SmsModuleInterface {
  isAvailable(): boolean;
  requestPermissions(): Promise<boolean>;
  hasPermissions(): Promise<boolean>;
  /** One-shot backfill read, mirrors SmsImporter.importLastDays */
  readInboxSince(startTimeMillis: number): Promise<RawSms[]>;
  /** Subscribe to live incoming SMS, mirrors SMSReceiver */
  addListener(callback: (sms: RawSms) => void): () => void;
}

/**
 * Safe no-op stub used until the real native module (Step 3) is built and
 * linked. Import sites should always go through this wrapper, never the
 * native module directly, so iOS builds never crash on a missing module.
 */
export const SmsModule: SmsModuleInterface = {
  isAvailable: () => Platform.OS === "android",
  requestPermissions: async () => false,
  hasPermissions: async () => false,
  readInboxSince: async () => [],
  addListener: () => () => {},
};
