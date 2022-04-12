package com.example.ratrofitdatawithimage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ratrofitdatawithimage.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var filepath_uri: Uri? = null
    private lateinit var bitMap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSelectImage.setOnClickListener {
            mGetContent.launch("image/*")
        }

        binding.btInsert.setOnClickListener {

        }
    }

    private val mGetContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri != null) {
                Glide
                    .with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.ivImage)
                filepath_uri = uri

                val input_stream = uri?.let { contentResolver.openInputStream(it) }
                bitMap = BitmapFactory.decodeStream(input_stream)

                imageStore(bitMap)

            }


        }

    private fun imageStore(bitMap: Bitmap?) {

        val stream = ByteArrayOutputStream()
        bitMap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)

    }
}