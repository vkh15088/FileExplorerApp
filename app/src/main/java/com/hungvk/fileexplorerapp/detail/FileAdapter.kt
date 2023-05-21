package com.hungvk.fileexplorerapp.detail

import android.graphics.BitmapFactory
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hungvk.fileexplorerapp.R
import com.hungvk.fileexplorerapp.common.FileUtils
import com.hungvk.fileexplorerapp.databinding.FileContainerBinding
import java.io.File

class FileAdapter(
    var fileList: List<File>,
    var listener: OnFileClickListener
): RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(val binding: FileContainerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = FileContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {

        val selectedFile = fileList.get(position)

        //Set file name
        holder.binding.txtFileName.text = fileList.get(position).name

        //Set file size
        setSize(selectedFile, holder)

        //Set appropriate image
        setImage(selectedFile, holder)

        //Set click listener
        setListener(selectedFile, holder)
    }

    private fun setListener(selectedFile: File, holder: FileViewHolder) {
        holder.binding.linearFileContainer.setOnClickListener {view ->
            listener.onFileClicked(selectedFile)
        }
    }

    private fun setImage(selectedFile: File, holder: FileViewHolder) {
        when(selectedFile.extension){
            "jpeg", "jpg", "png" ->  {
                val originalBitmap = BitmapFactory.decodeFile(selectedFile.absolutePath)
                val resizedBitmap = FileUtils.getResizedBitmap(originalBitmap, 50)
                holder.binding.imgFile.setImageBitmap(resizedBitmap)
            }
             "pdf" -> holder.binding.imgFile.setImageResource(R.drawable.pdf_image)
             "doc" -> holder.binding.imgFile.setImageResource(R.drawable.doc_image)
             "mp3", "wav" -> holder.binding.imgFile.setImageResource(R.drawable.audio_image)
             "mp4" -> holder.binding.imgFile.setImageResource(R.drawable.video_image)
             "apk" -> holder.binding.imgFile.setImageResource(R.drawable.android_image)
            else ->  holder.binding.imgFile.setImageResource(R.drawable.folder_image)

        }

    }

    private fun setSize(selectedFile: File, holder: FileViewHolder) {

        if (!selectedFile.isDirectory){
            //Set file size
            holder.binding.txtFileSize.text = Formatter.formatShortFileSize(holder.itemView.context, selectedFile.length())
            return
        }

        //Set folder size
        var itemCount = 0
        selectedFile.listFiles()?.forEach { file ->
            if (!file.isHidden){
                itemCount ++
            }
        }
        holder.binding.txtFileSize.text = "$itemCount files"
    }
}