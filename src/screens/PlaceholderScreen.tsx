import React from "react";
import { View, Text } from "react-native";
import { useTheme } from "@/theme/theme";

/** Shared stub used by screens not yet wired to real data/logic. */
export default function PlaceholderScreen({
  title,
  note,
}: {
  title: string;
  note: string;
}) {
  const { colors, spacing, typography } = useTheme();

  return (
    <View
      style={{
        flex: 1,
        backgroundColor: colors.background,
        padding: spacing.md,
        gap: spacing.sm,
      }}
    >
      <Text style={[typography.h1, { color: colors.textPrimary }]}>{title}</Text>
      <Text style={[typography.body, { color: colors.textSecondary }]}>{note}</Text>
    </View>
  );
}
