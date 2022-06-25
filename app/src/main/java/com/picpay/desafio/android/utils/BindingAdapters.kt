package com.picpay.desafio.android

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.users.UserListAdapter

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:items")
fun items(recyclerView: RecyclerView, users: List<UserModel>?) {
    val adapter = recyclerView.adapter as UserListAdapter
    adapter.users = if (users.isNullOrEmpty()) emptyList() else users
}