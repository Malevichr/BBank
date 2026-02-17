package ru.malevichrp.bbank.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResourceWrapper @Inject constructor(@param:ApplicationContext private val context: Context) {
    fun string(@StringRes id: Int) = context.getString(id)
}