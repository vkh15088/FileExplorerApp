package com.hungvk.fileexplorerapp.initial

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.hungvk.fileexplorerapp.R

class HomeFragment : Fragment() {

    private lateinit var alertDialog: AlertDialog

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()){}

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        runtimePermission()
    }


    override fun onResume() {
        super.onResume()

        checkPermissions()

    }

    private fun runtimePermission(){
        //Request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_VIDEO) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_AUDIO) ){

                activityResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO,
                        Manifest.permission.READ_MEDIA_AUDIO)
                )

            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                activityResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                )
            }
        } else {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                activityResultLauncher.launch(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                )
            }
        }
    }

    private fun checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_VIDEO) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_AUDIO)){

                if (!alertDialog.isShowing){
                    alertDialog.show()
                }
                return
            }

            alertDialog.cancel()
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                if (!alertDialog.isShowing){
                    alertDialog.show()
                }
                return
            }

            alertDialog.cancel()
        } else {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){

                if (!alertDialog.isShowing){
                    alertDialog.show()
                }
                return
            }

            alertDialog.cancel()
        }

    }

    private fun initView() {
        initAlertDialog()

    }

    private fun initAlertDialog(){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.dialog_require_permission)
        builder.setCancelable(false)
        builder.setPositiveButton(R.string.ok
            ) { dialogInterface, i ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri = Uri.fromParts("package", requireActivity().packageName , null)
                intent.setData(uri)
                startActivity(intent)
            }
        alertDialog = builder.create()
    }


}