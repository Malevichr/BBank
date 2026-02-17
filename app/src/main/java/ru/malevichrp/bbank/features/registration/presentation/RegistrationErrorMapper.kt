package ru.malevichrp.bbank.features.registration.presentation


import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.StringResourceWrapper
import ru.malevichrp.bbank.features.registration.domain.RegistrationDomainError
import javax.inject.Inject

class RegistrationErrorMapper @Inject constructor(
    private val resourceWrapper: StringResourceWrapper
) :
    RegistrationDomainError.Mapper<String> {
    override fun mapCommonError(): String =
        resourceWrapper.string(R.string.common_error)
}
