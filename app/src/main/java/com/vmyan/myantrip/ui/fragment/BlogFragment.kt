package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.SharePost
import com.vmyan.myantrip.ui.adapter.PostListAdapter
import com.vmyan.myantrip.ui.viewmodel.BlogViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.fragment_blog.view.*
import org.koin.android.ext.android.inject


class BlogFragment : Fragment(),PostListAdapter.ItemClickListener {

    private  val viewModel: BlogViewModel by inject()

    private lateinit var postListAdapter: PostListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_blog, container, false)

        setUpPostRecycler(view)
        setUpObserve()

        view.share_btn.setOnClickListener {
            val intent = Intent(context, SharePost::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun setUpPostRecycler(view: View) {
        postListAdapter = PostListAdapter(this, mutableListOf())
        view.post_recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        view.post_recyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        view.post_recyclerView.adapter = postListAdapter
    }

    @SuppressLint("showToast")
    private fun setUpObserve() {
        viewModel.fetchAllPost().observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {


                    blog_postitem_placeholder.startShimmer()
                    blog_postitem_placeholder.visibility = View.VISIBLE
                    post_recyclerView.visibility = View.GONE
                }
                is Resource.Success -> {


                    blog_postitem_placeholder.stopShimmer()
                   blog_postitem_placeholder.visibility = View.GONE
                    post_recyclerView.visibility = View.VISIBLE
                    postListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    blog_postitem_placeholder.startShimmer()
                    blog_postitem_placeholder.visibility = View.VISIBLE
                    post_recyclerView.visibility = View.GONE
                    Toast.makeText(activity, "An error is ocurred:${it.message}",Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onPostClick(position: Int) {

    }
}