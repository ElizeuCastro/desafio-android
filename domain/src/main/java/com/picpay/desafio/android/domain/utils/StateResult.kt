package com.picpay.desafio.android.domain.utils

sealed class StateResult<T : Any>

class Success<T : Any>(val data: T) : StateResult<T>()
class Error<T : Any>(val message: String?) : StateResult<T>()