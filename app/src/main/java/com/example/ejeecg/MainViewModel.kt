package com.example.ejeecg

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.*

class MainViewModel: ViewModel() {

    private val _resultado: MutableLiveData<String>? by lazy { MutableLiveData<String>() }
    fun getresultado() = _resultado as LiveData<String>
    fun setresultado(uno: Int, tres: Int){
       _resultado?.value = miCalculo(uno,tres).toString()
    }

    /*
    Fórmula ángulo =  = tan−1 [(DI + 2DIII)/√3 × DI]
     */

    private fun miCalculo(D1: Int, D3: Int): Int{

        val m = (D1+(2*D3))
        val n = D1 * sqrt(3.0)
        val f = m/n
        val x = atan(f)

        val angulo = x * (180/ PI)
        val final = when{
            D1<0 && D3>0 -> if(m>0){180 + angulo} else {angulo - 180}
            D1<0 && D3<0 -> angulo-180
            else -> angulo
        }
        return final.toInt()
    }

}