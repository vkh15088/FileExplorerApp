package com.hungvk.fileexplorerapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hungvk.fileexplorerapp.R
import com.hungvk.fileexplorerapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), OnDetailFragmentListener{

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailFragment = DetailFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun setPath(path: String) {
        binding.txtPath.text =  path
    }


//    private fun setActionBarSetting(
//        toolbar: Toolbar?, title: String?, isEnable: Boolean,
//        visibility: Boolean
//    ) {
//        setSupportActionBar(toolbar)
//
//        // ツールバーの設定を実行。各画面にまかせる
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setTitle(title)
//            actionBar.setDisplayHomeAsUpEnabled(isEnable)
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_titlebar_back)
//            if (!visibility) {
//                actionBar.hide()
//            }
//        }
//    }
}