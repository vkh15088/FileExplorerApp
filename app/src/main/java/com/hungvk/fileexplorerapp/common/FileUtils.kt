package com.hungvk.fileexplorerapp.common

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File


class FileUtils {
    companion object {
        fun openFile(context:Context, file:File) {
            val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)

            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            if (uri.toString().contains(".doc")){
                intent.setDataAndType(uri, "application/msword")
            } else if (uri.toString().contains(".pdf")) {
                intent.setDataAndType(uri, "application/pdf")
            } else if (uri.toString().contains(".mp3") || uri.toString().contains(".wav")) {
                intent.setDataAndType(uri, "audio/*")
            } else if (uri.toString().contains(".jpeg") || uri.toString().contains(".jpg") ||
                uri.toString().contains(".png")) {
                intent.setDataAndType(uri, "image/*")
            } else if (uri.toString().contains(".mp4")) {
                intent.setDataAndType(uri, "video/*")
            } else {
                intent.setDataAndType(uri, "*/*")
            }

            context.startActivity(intent)
        }


        fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }
    }


}