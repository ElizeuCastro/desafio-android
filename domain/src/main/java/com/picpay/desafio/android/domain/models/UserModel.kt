package com.picpay.desafio.android.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val img: String,
    val name: String,
    val id: Long,
    val username: String
) : Parcelable