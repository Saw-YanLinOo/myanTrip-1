package com.vmyan.myantrip.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.GetPost
import com.vmyan.myantrip.model.Word
import kotlinx.android.synthetic.main.commu_category_recycler.view.*
import kotlinx.android.synthetic.main.commu_word_recycler.view.*

class CommWordAdapter(private val listener: CommWordAdapter.ItemClickListener, private val wordList : MutableList<Word>) : RecyclerView.Adapter<CommWordAdapter.CategoryViewHolder>(){

    interface ItemClickListener{
        fun onWordClick(word: Word)
        fun onWordLongClick(word: Word)
    }

    fun setItems(wordList: List<Word>){
        this.wordList.clear()
        this.wordList.addAll(wordList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commu_word_recycler,parent,false)
        return CategoryViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(wordList[position])
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    class CategoryViewHolder(private val view : View, private val listener : ItemClickListener) : RecyclerView.ViewHolder(view),View.OnClickListener,View.OnLongClickListener {

        lateinit var word : Word
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(word : Word){
            this.word = word
            var language = Hawk.get<String>("language","Myanmar")
            if (language.trim() == "English"){
                itemView.tv_word.text = word.eng
            }else if (language.trim() == "Myanmar"){
                itemView.tv_word.text = word.myn
            }
        }

        override fun onClick(p0: View?) {
            listener.onWordClick(word)
        }

        override fun onLongClick(p0: View?): Boolean {
            listener.onWordLongClick(word)
            return true
        }
    }
}