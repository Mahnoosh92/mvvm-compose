package com.example.shoppingapp.utils.string

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultStringResolver @Inject constructor(@ApplicationContext val appContext: Context) :
    StringResolver {
    override fun getString(key: Int): String = appContext.getString(key)
}