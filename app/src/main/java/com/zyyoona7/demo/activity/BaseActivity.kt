package com.zyyoona7.demo.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.zyyoona7.demo.R

/**
 * @author   zyyoona7
 * @version  v1.0
 * @since    2018/12/27.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_base)
        recyclerView.layoutManager = createLayoutManager()

        val adapter = createAdapter()
        recyclerView.adapter = adapter
        addHeaderFooter(adapter)

        initItemDecoration(recyclerView,adapter)

        initData(adapter)
    }

    abstract fun createLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager

    abstract fun createAdapter(): androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>

    abstract fun addHeaderFooter(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>)

    abstract fun initItemDecoration(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>)

    abstract fun initData(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder>)

    fun getView(@LayoutRes layoutRes:Int):View{
        return LayoutInflater.from(this).inflate(layoutRes,null)
    }
}