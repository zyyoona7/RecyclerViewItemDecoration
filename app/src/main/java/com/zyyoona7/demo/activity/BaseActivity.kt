package com.zyyoona7.demo.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
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

        val recyclerView = findViewById<RecyclerView>(R.id.rv_base)
        recyclerView.layoutManager = createLayoutManager()

        val adapter = createAdapter()
        recyclerView.adapter = adapter
        addHeaderFooter(adapter)

        initItemDecoration(recyclerView,adapter)

        initData(adapter)
    }

    abstract fun createLayoutManager(): RecyclerView.LayoutManager

    abstract fun createAdapter(): RecyclerView.Adapter<BaseViewHolder>

    abstract fun addHeaderFooter(adapter: RecyclerView.Adapter<BaseViewHolder>)

    abstract fun initItemDecoration(recyclerView: RecyclerView,adapter: RecyclerView.Adapter<BaseViewHolder>)

    abstract fun initData(adapter: RecyclerView.Adapter<BaseViewHolder>)

    fun getView(@LayoutRes layoutRes:Int):View{
        return LayoutInflater.from(this).inflate(layoutRes,null)
    }
}