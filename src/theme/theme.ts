import { useColorScheme } from "react-native";
import { darkColors, lightColors, ThemeColors } from "./colors";

export const spacing = {
  xs: 4,
  sm: 8,
  md: 16,
  lg: 24,
  xl: 32,
};

export const radius = {
  sm: 8,
  md: 12,
  lg: 16,
  pill: 999,
};

export const typography = {
  h1: { fontSize: 28, fontWeight: "700" as const },
  h2: { fontSize: 20, fontWeight: "600" as const },
  body: { fontSize: 15, fontWeight: "400" as const },
  bodyStrong: { fontSize: 15, fontWeight: "600" as const },
  caption: { fontSize: 13, fontWeight: "400" as const },
};

export interface Theme {
  colors: ThemeColors;
  spacing: typeof spacing;
  radius: typeof radius;
  typography: typeof typography;
  isDark: boolean;
}

/** Central theme hook — every screen pulls colors from here, never a raw hex. */
export function useTheme(): Theme {
  const scheme = useColorScheme();
  const isDark = scheme === "dark";

  return {
    colors: isDark ? darkColors : lightColors,
    spacing,
    radius,
    typography,
    isDark,
  };
}
