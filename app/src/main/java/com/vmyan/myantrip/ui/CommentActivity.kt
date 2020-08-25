package com.vmyan.myantrip.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Comments
import com.vmyan.myantrip.ui.adapter.CommentListAdapter
import com.vmyan.myantrip.ui.viewmodel.CommentViewModel
import com.vmyan.myantrip.ui.viewmodel.CommunicationDetailViewModel
import com.vmyan.myantrip.utils.LoadingDialog
import com.vmyan.myantrip.utils.Resource
import com.vmyan.myantrip.utils.showToast
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.context_profile.*
import org.koin.android.ext.android.inject

class CommentActivity : AppCompatActivity(),CommentListAdapter.ItemClickListener {

    private  val viewModel: CommentViewModel by inject()
    private lateinit var commentListAdapter: CommentListAdapter
    var postId =  ""
    var postComment = 0
    private val loadingDialog = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        postId = intent.getStringExtra("postId").toString()
        postComment = intent.getIntExtra("postComment",0)

        setUpCommentRecycler()
        setUpObserve(postId,postComment)
    }

    private fun setUpCommentRecycler() {
        commentListAdapter = CommentListAdapter(this, mutableListOf())
        comment_recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        comment_recycler_view.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        comment_recycler_view.adapter = commentListAdapter
    }

    private fun setUpObserve(postId : String,postComments : Int){
        viewModel.getComment(postId).observe(this,{
            when(it){
                is Resource.Loading -> {
                    println("Loading Fetch Comment")
               loadingDialog.startLoading()
                }
                is Resource.Success -> {
                    loadingDialog.stopLoading()
                    commentListAdapter.setItems(it.data)
                }
                is Resource.Failure ->{
                    loadingDialog.stopLoading()
                    println("Fetch Comment Failed ==> ${it.message}")
                }
            }
        })
    }


    fun setComment(view: View) {
        var userId = Hawk.get<String>("user_id")
        var description = et_comment.text.toString()

        viewModel.uploadComment(postId,userId,description,postComment).observe(this,{
            when(it){
                is Resource.Loading -> {
                    loadingDialog.startLoading()
                }
                is Resource.Success -> {
                   loadingDialog.stopLoading()
                    showToast("Success!",Toast.LENGTH_SHORT)
                    et_comment.text.clear()
                    commentListAdapter.setItems(it.data)
                }
                is Resource.Failure ->{
                    loadingDialog.stopLoading()
                    println("${it.message}")
                }
            }
        })
    }

    override fun onItemClick(comments: Comments) {

    }
}