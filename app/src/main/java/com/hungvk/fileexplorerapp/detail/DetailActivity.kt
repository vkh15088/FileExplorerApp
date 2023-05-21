package com.hungvk.fileexplorerapp.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hungvk.fileexplorerapp.R
import com.hungvk.fileexplorerapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), OnDetailFragmentListener{

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        if (intent != null){
            setActionBarSetting(binding.toolbar, intent.getStringExtra("title"),true)
        }

        val detailFragment = DetailFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun setPath(path: String) {
        binding.txtPath.text =  path
    }


    private fun setActionBarSetting(toolbar: Toolbar?, title: String?, isEnable: Boolean) {
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(isEnable)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
            if (supportFragmentManager.backStackEntryCount == 0){
            finish()
        }
    }
}