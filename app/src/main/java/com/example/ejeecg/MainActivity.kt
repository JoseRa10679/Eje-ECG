package com.example.ejeecg

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ejeecg.databinding.ActivityMainBinding
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    //<editor-folder desc = " Menu ">

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        if (item.itemId == R.id.action_settings) {
            Toast.makeText(applicationContext,"Calcula el eje eléctrico del ECG\n\n@JoseRa\nquistabenasque@gmail.com", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                view.setPadding(50,50,50,50)
                view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.teal_200))
                show()
            }

        } else if (item.itemId == R.id.version) {
            var packageinfo: PackageInfo? = null
            try {
                packageinfo = packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            var version: String? = null
            if (packageinfo != null) {
                version = packageinfo.versionName
            }
            Toast.makeText(this@MainActivity, "Eje ECG versión: $version", Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.TOP or Gravity.CENTER, 0, 0)
                view.setPadding(50,50,50,50)
                view.setBackgroundColor(ContextCompat.getColor(view.context,R.color.teal_200))
                show()
            }

        }
        return false
    }

    //</editor-folder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run{
            setContentView(root)
            setSupportActionBar(toolbar)
            editTextDI.requestFocus()
        }

        /**
        Entre -30º y 90º el eje es normal.
        Entre 90º y 180º el eje está desviado a la derecha.
        Entre -30º y -90º el eje está desviado a la izquierda.
        Entre -90º y -180º el eje tiene desviación extrema.
        */

        mViewModel.getresultado().observe(this,{
            binding.run{
                when{
                    it.toInt()<-30 && it.toInt()>-90 -> {
                        textResultado.setTextColor(getColor(R.color.red))
                        "$it grados\nEje desviado a la izquierda".also {textResultado.text = it }
                    }
                    it.toInt()>=-30 && it.toInt()<=90 ->{
                        textResultado.setTextColor(getColor(R.color.teal_200))
                        "$it grados\nEje normal".also {textResultado.text = it }
                    }
                    it.toInt()>90 && it.toInt()<=180 ->{
                        textResultado.setTextColor(getColor(R.color.teal_200))
                        "$it grados\nEje normal".also {textResultado.text = it }
                    }
                    else -> {
                        textResultado.setTextColor(getColor(R.color.red))
                        "$it grados\nDesviación del eje extrema".also {textResultado.text = it }
                    }
                }
            }

        })

        binding.button.setOnClickListener {
            if(!binding.editTextDI.text.isNullOrEmpty() && !binding.editTextD3.text.isNullOrEmpty()){
                val uNo = binding.editTextDI.text.toString().toInt()
                val tRes = binding.editTextD3.text.toString().toInt()
                mViewModel.setresultado(uNo,tRes)
                UIUtil.hideKeyboard(this)
            }else{
                Toast.makeText(this,"Hay que completar los valores de DI y DIII", Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
                    view.run{
                        setPadding(50,50,50,50)
                        setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
                    }

                    show()
                }
            }

        }

    }

}