import React from "react";
import { Platform, Text } from "react-native";
import { NavigationContainer, DefaultTheme, DarkTheme } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";

import { useTheme } from "@/theme/theme";
import { RootStackParamList, TabParamList } from "./types";

import HomeScreen from "@/screens/home/HomeScreen";
import TransactionsScreen from "@/screens/transactions/TransactionsScreen";
import BudgetsScreen from "@/screens/budgets/BudgetsScreen";
import AnalyticsScreen from "@/screens/analytics/AnalyticsScreen";
import SettingsScreen from "@/screens/settings/SettingsScreen";
import AddTransactionScreen from "@/screens/addTransaction/AddTransactionScreen";

const Tab = createBottomTabNavigator<TabParamList>();
const Stack = createNativeStackNavigator<RootStackParamList>();

const TAB_ICON: Record<keyof TabParamList, string> = {
  Home: "🏠",
  Transactions: "🧾",
  Budgets: "💼",
  Analytics: "📊",
  Settings: "⚙️",
};

function Tabs() {
  const theme = useTheme();

  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        headerShown: true,
        headerStyle: { backgroundColor: theme.colors.surface },
        headerTitleStyle: { color: theme.colors.textPrimary },
        tabBarStyle: {
          backgroundColor: theme.colors.surface,
          borderTopColor: theme.colors.border,
        },
        tabBarActiveTintColor: theme.colors.primary,
        tabBarInactiveTintColor: theme.colors.tabInactive,
        tabBarIcon: () => (
          <Text style={{ fontSize: 18 }}>
            {TAB_ICON[route.name as keyof TabParamList]}
          </Text>
        ),
      })}
    >
      <Tab.Screen name="Home" component={HomeScreen} />
      <Tab.Screen name="Transactions" component={TransactionsScreen} />
      <Tab.Screen name="Budgets" component={BudgetsScreen} />
      <Tab.Screen name="Analytics" component={AnalyticsScreen} />
      {/* SMS-related settings only meaningfully apply on Android; the screen
          itself hides the permission card on iOS (see Step 11). */}
      <Tab.Screen name="Settings" component={SettingsScreen} />
    </Tab.Navigator>
  );
}

export default function RootNavigator() {
  const theme = useTheme();

  const navTheme = {
    ...(theme.isDark ? DarkTheme : DefaultTheme),
    colors: {
      ...(theme.isDark ? DarkTheme.colors : DefaultTheme.colors),
      background: theme.colors.background,
      card: theme.colors.surface,
      text: theme.colors.textPrimary,
      border: theme.colors.border,
      primary: theme.colors.primary,
    },
  };

  return (
    <NavigationContainer theme={navTheme}>
      <Stack.Navigator>
        <Stack.Screen name="Tabs" component={Tabs} options={{ headerShown: false }} />
        <Stack.Screen
          name="AddTransaction"
          component={AddTransactionScreen}
          options={{
            presentation: Platform.OS === "ios" ? "modal" : "card",
            title: "Add Transaction",
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
