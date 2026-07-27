package com.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expensetracker.repository.TransactionRepository


class ViewModelFactory(
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {


        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {

            return HomeViewModel(
                transactionRepository
            ) as T

        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )

    }

}