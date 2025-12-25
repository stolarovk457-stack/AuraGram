package com.aura.auragram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aura.auragram.ui.AuraTheme
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.aura.auragram.data.AppDatabase
import com.aura.auragram.data.MessageRepository
import com.aura.auragram.td.MockTdlibAdapter
import com.aura.auragram.data.PreferencesRepository
import com.aura.auragram.data.StickerRepository
import com.aura.auragram.ui.navigation.SetupNavGraph

class MainActivity : ComponentActivity() {

    private lateinit var repository: MessageRepository
    private lateinit var mockTdlib: MockTdlibAdapter
    private lateinit var prefsRepo: PreferencesRepository
    private lateinit var stickerRepo: StickerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(applicationContext)
        repository = MessageRepository(db.messageDao())

        // Preferences repository (DataStore)
        prefsRepo = PreferencesRepository(applicationContext)

        // Sticker repository
        stickerRepo = StickerRepository(db.stickerDao(), applicationContext)

        // Запускаем мок адаптер, он будет сохранять сообщения в базу и эмулировать удаление
        mockTdlib = MockTdlibAdapter(repository)
        lifecycleScope.launch(Dispatchers.IO) {
            mockTdlib.startEmulation()
        }

        setContent {
            AuraTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, repository = repository, prefs = prefsRepo, stickers = stickerRepo)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mockTdlib.stopEmulation()
    }
}
