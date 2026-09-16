import React from "react";
import { ScrollView, Text, View, StyleSheet } from "react-native";
import { useTheme } from "@/theme/theme";

/**
 * Placeholder for Step 1. Real data (today's summary, recent transactions)
 * gets wired in once the SQLite data layer (Step 2) exists.
 */
export default function HomeScreen() {
  const { colors, spacing, radius, typography } = useTheme();

  return (
    <ScrollView
      style={{ flex: 1, backgroundColor: colors.background }}
      contentContainerStyle={{ padding: spacing.md, gap: spacing.md }}
    >
      <View
        style={[
          styles.card,
          { backgroundColor: colors.surface, borderRadius: radius.lg, borderColor: colors.border },
        ]}
      >
        <Text style={[typography.h2, { color: colors.textPrimary }]}>Today's Summary</Text>
        <Text style={[typography.caption, { color: colors.textSecondary, marginTop: 4 }]}>
          Wired up in Step 2 (data layer)
        </Text>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  card: { padding: 16, borderWidth: StyleSheet.hairlineWidth },
});
