package ru.malevichrp.bbank.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.malevichrp.bbank.features.home.domain.HomeResult
import ru.malevichrp.bbank.features.home.items.profile.FullNameRepository
import ru.malevichrp.bbank.features.profile.data.AvatarRepository
import ru.malevichrp.bbank.features.profile.data.AvatarResult
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameRepository: FullNameRepository,
    private val avatarRepository: AvatarRepository
) : ViewModel() {
    private val _state: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Error)
    private val retry = MutableSharedFlow<Unit>(extraBufferCapacity = 1)


    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProfileUiState> = retry.onStart { emit(Unit) }
        .flatMapLatest {
            val name = nameRepository.load()
            val avatar = avatarRepository.load()
            combine(name, avatar) { name, avatar ->
                when {
                    name is HomeResult.Error -> {
                        TODO("replase all with Asyncer")
                    }
                }
                if (name is HomeResult.Error || avatar is AvatarResult.Error)
                    ProfileUiState.Error
                else ProfileUiState.Success(
                    (name as HomeResult.Success<String>).data,
                    (avatar as AvatarResult.Success).data
                )
            }.onStart { emit(ProfileUiState.Loading) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState.Loading
        )
    private val _errorOccurred = MutableSharedFlow<String>(
        extraBufferCapacity = 1
    )
    val errorOccurred = _errorOccurred.asSharedFlow()

    fun retry() {

    }

    fun logout() {

    }
}
