package ru.malevichrp.bbank.features.login.presentation


import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.StringResourceWrapper
import ru.malevichrp.bbank.features.login.domain.DomainError
import javax.inject.Inject

class ErrorResourceMapper @Inject constructor(
    private val resourceWrapper: StringResourceWrapper
) : DomainError.Mapper<String> {

    override fun mapWrongLoginData(): String =
        resourceWrapper.string(R.string.wrong_login_data_error)

}