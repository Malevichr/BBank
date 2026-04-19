package ru.malevichrp.bbank.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import ru.malevichrp.bbank.core.LoadResult
import ru.malevichrp.bbank.core.LoadableUiState
import ru.malevichrp.bbank.coreui.DomainErrorMapper
import ru.malevichrp.bbank.features.home.items.profile.FullNameRepository
import ru.malevichrp.bbank.features.profile.data.AvatarRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val nameRepository: FullNameRepository,
    private val avatarRepository: AvatarRepository,
    private val errorMapper: DomainErrorMapper,
) : ViewModel() {
    private val retry = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val _errorOccurred = MutableSharedFlow<String>(
        extraBufferCapacity = 1
    )
    val errorOccurred = _errorOccurred.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<LoadableUiState<ProfileData>> = retry.onStart { emit(Unit) }
        .flatMapLatest {
            val name = nameRepository.load()
            val avatar = avatarRepository.load()

            combine(name, avatar) { name, avatar ->
                if (name is LoadResult.Error) {

                    _errorOccurred.emit(name.domainError.map(errorMapper))
                    LoadableUiState.Error
                } else if (avatar is LoadResult.Error) {
                    _errorOccurred.emit(avatar.domainError.map(errorMapper))
                    LoadableUiState.Error
                } else LoadableUiState.Success(
                    ProfileData(
                        (name as LoadResult.Success<String>).data,
                        (avatar as LoadResult.Success).data
                    )
                )
            }.onStart { emit(LoadableUiState.Loading) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoadableUiState.Loading
        )

    fun retry() {
        retry.tryEmit(Unit)
    }

    fun logout() {
        //todo add LogoutUseCase
    }
}
