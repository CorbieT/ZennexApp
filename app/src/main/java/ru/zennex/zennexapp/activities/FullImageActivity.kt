package ru.zennex.zennexapp.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_full_image.*
import ru.zennex.zennexapp.R


class FullImageActivity : AppCompatActivity() {
    private val maximumScale = 10.0f
    private val minimumScale = 1.5f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        val imageByteArray = intent.getByteArrayExtra("imageByteArray")
        val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
        imageBitmap?.let { fullPhotoView.setImageBitmap(it) }
        fullPhotoView.maximumScale = 15.0f
        fullPhotoView.minimumScale = 1.0f

        zoomUpButton.setOnClickListener {
            if (fullPhotoView.scale <= maximumScale) {
                fullPhotoView.setScale(fullPhotoView.scale + 0.5f, true)
            }
        }

        zoomDownButton.setOnClickListener {
            if (fullPhotoView.scale >= minimumScale) {
                fullPhotoView.setScale(fullPhotoView.scale - 0.5f, true)
            }
        }
    }
}