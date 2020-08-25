package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.User
import com.vmyan.myantrip.ui.PostUploadActivity
import com.vmyan.myantrip.ui.Profile
import com.vmyan.myantrip.ui.SharePost
import com.vmyan.myantrip.ui.adapter.PostListAdapter
import com.vmyan.myantrip.ui.adapter.UserListAdapter
import com.vmyan.myantrip.ui.viewmodel.BlogViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.NoAccountDialog
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.fragment_blog.view.*
import kotlinx.android.synthetic.main.post_recyclerviews.view.*
import org.koin.android.ext.android.inject


class BlogFragment : Fragment(),PostListAdapter.ItemClickListener,UserListAdapter.ItemClickListener {

    private  val viewModel: BlogViewModel by inject()
    private lateinit var postListAdapter: PostListAdapter
    private lateinit var userListAdapter: UserListAdapter
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_blog, container, false)

        val userid = auth.currentUser?.uid.toString().trim()

        if (userid == "null"){
            NoAccountDialog(requireContext()).noAccountDialog()
        }else{

            var userName = Hawk.get<String>("user_name")
            var userImage = Hawk.get<String>("user_profile")
            view.user_name_b.text = userName
            Glide.with(requireContext()) //1
                .load(userImage)
                .placeholder(R.drawable.ic_account_circle)
                .transform(CircleCrop()) //4
                .into(view.user_image_b)

            setUpUserRecycler(view)
            setUpPostRecycler(view)
            setUpObserve(view)
        }


        view.user_image_b.setOnClickListener {
            val intent = Intent(context, PostUploadActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun setUpUserRecycler(view: View) {
        userListAdapter = UserListAdapter(this, mutableListOf())
        view.user_recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        view.user_recyclerView.setHasFixedSize(true)
        view.user_recyclerView.adapter = userListAdapter
    }

    private fun setUpPostRecycler(view: View) {
        postListAdapter = PostListAdapter(this, mutableListOf())
        view.post_recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        view.post_recyclerView.setHasFixedSize(true)
        view.post_recyclerView.adapter = postListAdapter
    }

    @SuppressLint("showToast")
    private fun setUpObserve(view: View) {
        viewModel.getAllUser().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    view.blog_user_item_placeholder.startShimmer()
                    view.blog_user_item_placeholder.visibility = View.VISIBLE
                    view.user_layout.visibility = View.GONE
                    println("loading.....UserList")
                }
                is Resource.Success -> {
                    println("GetUserList ==> ${it.data}")
                    view.blog_user_item_placeholder.stopShimmer()
                    view.blog_user_item_placeholder.visibility = View.GONE
                    view.user_layout.visibility = View.VISIBLE
                    userListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    view.blog_user_item_placeholder.startShimmer()
                    view.blog_user_item_placeholder.visibility = View.VISIBLE
                    view.user_layout.visibility = View.GONE
                    Toast.makeText(activity, "An error is ocurred:${it.message}",Toast.LENGTH_SHORT
                    )
                }
            }
        })
        viewModel.fetchAllPost().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    view.blog_postitem_placeholder.startShimmer()
                    view.blog_postitem_placeholder.visibility = View.VISIBLE
                    view.post_recyclerView.visibility = View.GONE
                    println("loading.....PostList")
                }
                is Resource.Success -> {
                    println("GetPostList ==> ${it.data}")
                    view.blog_postitem_placeholder.stopShimmer()
                    view.blog_postitem_placeholder.visibility = View.GONE
                    view.post_recyclerView.visibility = View.VISIBLE
                    postListAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    view.blog_postitem_placeholder.startShimmer()
                    view.blog_postitem_placeholder.visibility = View.VISIBLE
                    post_recyclerView.visibility = View.GONE
                    Toast.makeText(activity, "An error is ocurred:${it.message}",Toast.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
        setUpObserve(requireView())
        requireView().blog_postitem_placeholder.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        requireView().blog_postitem_placeholder.stopShimmer()
    }

    override fun onPostClick(position: Int) {
        println("Click Post")
    }

    override fun onItemClickListener(user : User) {
        var intent = Intent(requireContext(),Profile::class.java)
        intent.putExtra("user_id",user.user_id)
        intent.putExtra("user_name",user.username)
        intent.putExtra("user_profile",user.profilephoto)
        startActivity(intent)
    }
}