import type { NavigatorScreenParams } from "@react-navigation/native";

export type TabParamList = {
  Home: undefined;
  Transactions: undefined;
  Budgets: undefined;
  Analytics: undefined;
  Settings: undefined;
};

export type RootStackParamList = {
  Tabs: NavigatorScreenParams<TabParamList>;
  AddTransaction: undefined;
};
