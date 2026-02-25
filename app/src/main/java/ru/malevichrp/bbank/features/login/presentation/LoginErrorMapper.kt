package ru.malevichrp.bbank.features.login.presentation


import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.StringResourceWrapper
import ru.malevichrp.bbank.features.login.domain.LoginDomainError
import javax.inject.Inject

class LoginErrorMapper @Inject constructor(
    private val resourceWrapper: StringResourceWrapper
) : LoginDomainError.Mapper<String> {

    override fun mapWrongLoginData(): String =
        resourceWrapper.string(R.string.wrong_login_data_error)
}