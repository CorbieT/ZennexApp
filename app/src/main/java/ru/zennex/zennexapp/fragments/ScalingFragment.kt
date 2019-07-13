package ru.zennex.zennexapp.fragments

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_scaling.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.activities.FullImageActivity
import java.io.ByteArrayOutputStream
import java.io.File

class ScalingFragment : Fragment() {

    companion object {
        const val GALLERY_REQUEST_CODE = 1007
        const val CAMERA_REQUEST_CODE = 1008
    }

    private var imageUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scaling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryButton.setOnClickListener {
            val checkSelfPermission =
                ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                openAlbum()
            }
        }
        cameraButton.setOnClickListener {
            val outputImage = File(activity!!.externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(activity!!, "com.mobiledev.imageutils.fileprovider", outputImage)
            } else {
                Uri.fromFile(outputImage)
            }

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum()
                } else {
                    Toast.makeText(activity!!, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(activity!!.contentResolver.openInputStream(imageUri!!))
                    openFullImageActivity(bitmap)
                }
            GALLERY_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) {
                    handleImageOnKitkat(data)
                }
        }
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun openFullImageActivity(bitmap: Bitmap?) {
        val bStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        val byteArray = bStream.toByteArray()

        val imageIntent = Intent(activity!!, FullImageActivity::class.java)
        imageIntent.putExtra("imageByteArray", byteArray)
        activity!!.startActivity(imageIntent)
    }

    private fun imagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = activity!!.contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        if (DocumentsContract.isDocumentUri(activity!!, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri?.authority) {
                val id = docId.split(":")[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri?.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = imagePath(contentUri, null)
            }
        } else if ("content".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = imagePath(uri, null)
        } else if ("file".equals(uri?.scheme, ignoreCase = true)) {
            imagePath = uri?.path
        }
        openFullImageActivity(BitmapFactory.decodeFile(imagePath))
    }
}