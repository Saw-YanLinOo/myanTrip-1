package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.SharePost
import com.vmyan.myantrip.ui.adapter.PostListAdapter
import com.vmyan.myantrip.ui.viewmodel.BlogVMFactory
import com.vmyan.myantrip.ui.viewmodel.BlogViewModel
import com.vmyan.myantrip.ui.viewmodel.HomeVMFactory
import com.vmyan.myantrip.ui.viewmodel.HomeViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.fragment_blog.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class BlogFragment : Fragment(),PostListAdapter.ItemClickListener,DIAware {

    override val di: DI by closestDI()
    private val viewModelFactory: BlogVMFactory by instance()

    private lateinit var viewModel: BlogViewModel

    private lateinit var postListAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory).get(BlogViewModel::class.java)
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_blog, container, false)

        setUpPostRecycler(view)
        setUpObserve(view)

        return view
    }

    private fun setUpPostRecycler(view: View) {
        postListAdapter = PostListAdapter(this, mutableListOf())
        val layoutManager = activity?.let { ZoomRecyclerLayout(it) }
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        view.post_recyclerView.layoutManager = layoutManager
        view.post_recyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        view.post_recyclerView.adapter = postListAdapter
    }

    @SuppressLint("showToast")
    private fun setUpObserve(view: View) {
        viewModel.fetchPostList.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    view.blog_postitem_placeholder.startShimmer()
                    view.blog_postitem_placeholder.visibility = View.VISIBLE
                    view.post_recyclerView.visibility = View.GONE
                    println("loading.....PostList")
                }
                is Resource.Success -> {
                    view.blog_postitem_placeholder.stopShimmer()
                    view.blog_postitem_placeholder.visibility = View.GONE
                    view.post_recyclerView.visibility = View.VISIBLE
                    postListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    view.blog_postitem_placeholder.startShimmer()
                    view.blog_postitem_placeholder.visibility = View.VISIBLE
                    view.post_recyclerView.visibility = View.GONE
                    Toast.makeText(activity, "An error is ocurred:${it.message}",Toast.LENGTH_SHORT
                    )
                }
            }
        })

//        view.share_btn.setOnClickListener (View.OnClickListener {
//            val intent = Intent(context,SharePost::class.java)
//            startActivity(intent)
//        })
    }

    override fun onPostClick(position: Int) {

    }
}