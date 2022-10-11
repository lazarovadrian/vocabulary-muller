package com.lazarovstudio.vocabularymuller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lazarovstudio.vocabularymuller.databinding.ActivityMainBinding
import com.lazarovstudio.vocabularymuller.extension.openFragment
import com.lazarovstudio.vocabularymuller.fragments.ListAlphabetChar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            val listAlphabetChar = ListAlphabetChar.newInstance()
            //проверяем создался ли фрагмент в контейнере
            //если нет то создает новый - запускаем фрагмент
            openFragment(listAlphabetChar)
        }

//        val btn: Button = findViewById(R.id.btnSend)
//        val editText: EditText = findViewById(R.id.editSpace)

//        btn.setOnClickListener {
//            val text = editText.text
//            if(text.length > 5){
//                println(text)
//            }else{
//                Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}