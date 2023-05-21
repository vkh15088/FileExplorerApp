package com.hungvk.fileexplorerapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungvk.fileexplorerapp.databinding.FragmentDetailBinding
import java.io.File
import kotlin.collections.ArrayList


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var fileList: ArrayList<File>
    private lateinit var fileAdapter: FileAdapter
    private lateinit var storage: File
    private lateinit var listener: OnDetailFragmentListener
    private val fileExtensionList = listOf("jpeg", "jpg", "png", "mp3", "wav", "mp3", "wav", "mp4", "pdf", "doc", "apk")

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener = activity as DetailActivity
        if (listener == null){
            return
        }

        val internalStorage = System.getenv("EXTERNAL_STORAGE")
        storage = File(internalStorage)
        listener.setPath(storage.absolutePath)

        displayFiles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayFiles() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fileList = ArrayList(allFileList(storage))
        fileAdapter = FileAdapter(fileList)
        binding.recyclerView.adapter = fileAdapter
    }


    private fun allFileList(file: File): ArrayList<File>{
        val arrayList = ArrayList<File>()
        val files = file.listFiles()

        //Add Folders
        files.forEach {file ->
            if (file.isDirectory && !file.isHidden){
                arrayList.add(file)
            }
        }

        //Add files
        files.forEach {file ->
            val extenstionFile = file.name.split(".")
            if (fileExtensionList.contains(extenstionFile[extenstionFile.size-1])){
                arrayList.add(file)
            }
        }

        return arrayList
    }


}