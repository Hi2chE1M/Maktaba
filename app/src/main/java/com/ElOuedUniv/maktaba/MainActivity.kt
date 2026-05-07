package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import com.ElOuedUniv.maktaba.data.datastore.UserPreferencesRepository
import com.ElOuedUniv.maktaba.presentation.navigation.NavGraph
import com.ElOuedUniv.maktaba.presentation.navigation.Screen
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            MaktabaTheme {
                val hasCompletedOnboarding by produceState<Boolean?>(initialValue = null) {
                    userPreferencesRepository.hasCompletedOnboarding.collect { value = it }
                }

                hasCompletedOnboarding?.let { completed ->
                    val startDestination = if (completed) {
                        Screen.BookList.route
                    } else {
                        Screen.Onboarding.route
                    }
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}
