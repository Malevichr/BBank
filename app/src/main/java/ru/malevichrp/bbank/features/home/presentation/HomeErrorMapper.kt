package ru.malevichrp.bbank.features.home.presentation

import ru.malevichrp.bbank.R
import ru.malevichrp.bbank.core.StringResourceWrapper
import ru.malevichrp.bbank.features.home.domain.HomeDomainError
import javax.inject.Inject

class HomeErrorMapper @Inject constructor(private val resourceWrapper: StringResourceWrapper) :
    HomeDomainError.Mapper<String> {
    override fun mapCommon(): String = resourceWrapper.string(R.string.common_error)
}