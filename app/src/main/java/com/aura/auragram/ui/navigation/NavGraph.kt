package com.aura.auragram.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aura.auragram.data.MessageRepository
import com.aura.auragram.data.PreferencesRepository
import com.aura.auragram.data.StickerRepository
import com.aura.auragram.ui.screens.AuthScreen
import com.aura.auragram.ui.screens.ChatScreen
import com.aura.auragram.ui.screens.ChatsScreen
import com.aura.auragram.ui.screens.SavedDeletedScreen
import com.aura.auragram.ui.screens.SettingsScreen
import com.aura.auragram.ui.screens.SupportScreen
import com.aura.auragram.ui.screens.StickerCreatorScreen

@Composable
fun SetupNavGraph(
    navController: androidx.navigation.NavHostController,
    repository: MessageRepository,
    prefs: PreferencesRepository,
    stickers: StickerRepository
) {
    NavHost(navController = navController, startDestination = Routes.CHATS) {
        composable(Routes.AUTH) {
            AuthScreen(onAuthSuccess = { navController.navigate(Routes.CHATS) })
        }
        composable(Routes.CHATS) {
            ChatsScreen(onOpenChat = { chatId ->
                navController.navigate("chat/$chatId")
            }, onSaved = {
                navController.navigate(Routes.SAVED)
            }, onSettings = {
                navController.navigate(Routes.SETTINGS)
            }, onSupport = {
                navController.navigate(Routes.SUPPORT)
            }, onStickers = {
                navController.navigate(Routes.STICKERS)
            })
        }
        composable(Routes.CHAT,
            arguments = listOf(navArgument("chatId") { type = NavType.LongType })) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getLong("chatId") ?: 0L
            ChatScreen(chatId = chatId, repository = repository)
        }
        composable(Routes.SAVED) {
            SavedDeletedScreen(repository = repository)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(prefs = prefs)
        }
        composable(Routes.SUPPORT) {
            SupportScreen()
        }
        composable(Routes.STICKERS) {
            StickerCreatorScreen(stickers = stickers)
        }
    }
}