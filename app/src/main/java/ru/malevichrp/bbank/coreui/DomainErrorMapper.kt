package ru.malevichrp.bbank.coreui

import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.DomainError
import ru.malevichrp.bbank.core.StringResourceWrapper
import javax.inject.Inject

class DomainErrorMapper @Inject constructor(private val resourceWrapper: StringResourceWrapper) :
    DomainError.Mapper<String> {
    override fun mapCommon(): String = resourceWrapper.string(R.string.common_error)
    override fun mapMessaged(message: String): String = message
}