package com.example.ejeecg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.round
import kotlin.math.sqrt

class MainViewModel: ViewModel() {

    private val _resultado: MutableLiveData<String>? by lazy { MutableLiveData<String>() }
    fun getresultado() = _resultado as LiveData<String>
    fun setresultado(uno: Int, tres: Int){
       _resultado?.value = miCalculo(uno,tres).toString()
    }

    private fun miCalculo(D1: Int, D3: Int): Int{
        val angulo = round(atan((D1+D3)/(D1* sqrt(3.0))) * (180/ PI))
        return angulo.toInt()
    }

}