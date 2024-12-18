package com.example.pymestock.models

object CurrentUser {

    private var number: Int = -1

    fun getId(): Int {
        return number
    }

    fun setId(number: Int) {
        this.number = number
    }
}