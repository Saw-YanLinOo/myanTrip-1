package com.vmyan.myantrip.ui.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.Word
import com.vmyan.myantrip.model.WordCategory
import com.vmyan.myantrip.ui.CommunicationDetail
import com.vmyan.myantrip.ui.adapter.CommCategoryAdapter
import com.vmyan.myantrip.ui.adapter.CommWordAdapter
import com.vmyan.myantrip.ui.viewmodel.CommunicationViewModel
import com.vmyan.myantrip.utils.Resource
import kotlinx.android.synthetic.main.fragment_communication.*
import kotlinx.android.synthetic.main.fragment_communication.view.*
import org.koin.android.ext.android.inject


class CommunicationFragment : Fragment(),CommCategoryAdapter.ItemClickListener,CommWordAdapter.ItemClickListener {

    private  val viewModel: CommunicationViewModel by inject()
    private lateinit var imageAnimation: AnimationDrawable

    private lateinit var categoryAdapter : CommCategoryAdapter
    private lateinit var wordAdapter: CommWordAdapter
    private var fileList = ArrayList<String>()
    private var language = Hawk.get<String>("language","Myanmar")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_communication, container, false)

        view.tv_languages.text = language
        setUpAnimation(view)
        setUpCategoryRecycler(view)
        setUpWordRecycler(view)
        setUpObserve(view)

        return view
    }

    private fun setUpAnimation(view: View) {

        val imageView = view.findViewById<ImageView>(R.id.img_view_animation)
        imageView.apply {
            setBackgroundResource(R.drawable.speak_animation_list)
            imageAnimation = background as AnimationDrawable
        }
        imageAnimation.start()
    }

    private fun setUpCategoryRecycler(view: View) {
         categoryAdapter = CommCategoryAdapter(this, mutableListOf())
        view.categoryRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        //view.categoryRecyclerView.layoutManager = StaggeredGridLayoutManager(checkedColumnWidth(requireContext(),),StaggeredGridLayoutManager.VERTICAL)
        //view.categoryRecyclerView.layoutManager = layoutManager
        view.categoryRecyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        view.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun setUpWordRecycler(view: View) {
        wordAdapter = CommWordAdapter(this, mutableListOf())
        view.wordRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        view.wordRecyclerView.adapter = wordAdapter

    }

    private fun setUpObserve(view: View) {
        viewModel.getCategory().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("Loading Category")
                    view.category_placeholder.startShimmer()
                    view.category_placeholder.visibility = View.VISIBLE
                    view.categoryRecyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    view.category_placeholder.stopShimmer()
                    view.category_placeholder.visibility = View.GONE
                    view.categoryRecyclerView.visibility = View.VISIBLE
                    categoryAdapter.setItems(it.data)
                    println("${it.data}")
                }
                is Resource.Failure -> {
                    view.category_placeholder.startShimmer()
                    view.category_placeholder.visibility = View.GONE
                    view.categoryRecyclerView.visibility = View.VISIBLE
                    println(it.message)
                }
            }
        })

        viewModel.getWord().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("Loading Word")
                    view.word_placeholder.startShimmer()
                    view.word_placeholder.visibility = View.VISIBLE
                    view.wordRecyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    view.word_placeholder.stopShimmer()
                    view.word_placeholder.visibility = View.GONE
                    view.wordRecyclerView.visibility = View.VISIBLE
                    wordAdapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    view.word_placeholder.startShimmer()
                    view.word_placeholder.visibility = View.GONE
                    view.wordRecyclerView.visibility = View.VISIBLE
                    println(it.message)
                }
            }
        })
    }


    override fun onResume() {
        super.onResume()

        requireView().category_placeholder.startShimmer()
        requireView().word_placeholder.startShimmer()
    }

    override fun onPause() {
        super.onPause()

        requireView().category_placeholder.stopShimmer()
        requireView().word_placeholder.stopShimmer()
    }
    override fun onTitleClick(category: WordCategory) {

        viewModel.getWordByCategory(category.id!!).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    println("Loading Word")
                    word_placeholder.startShimmer()
                    word_placeholder.visibility = View.VISIBLE
                    wordRecyclerView.visibility = View.GONE
                }
                is Resource.Success -> {
                    word_placeholder.stopShimmer()
                    word_placeholder.visibility = View.GONE
                    wordRecyclerView.visibility = View.VISIBLE
                    wordAdapter.setItems(it.data)
                    println("${it.data}")
                }
                is Resource.Failure -> {
                    word_placeholder.startShimmer()
                    word_placeholder.visibility = View.GONE
                    wordRecyclerView.visibility = View.VISIBLE
                    println(it.message)
                }
            }
        })
    }

    override fun onWordClick(word: Word) {
        var gson: Gson = GsonBuilder().create()
        val intent = Intent(requireContext(), CommunicationDetail::class.java)
        intent.putExtra("word", gson.toJson(word))
        startActivity(intent)
    }

    override fun onWordLongClick(word: Word) {
        showCustomDialog()
    }

    @SuppressLint("SetTextI18n")
    fun showCustomDialog(){
        val countryTitle : MutableList<String> =ArrayList()
        countryTitle.add("Myanmar")
        countryTitle.add("English")

        val countryFlag: MutableList<Int> = ArrayList()
        countryFlag.add(R.drawable.ic_myanmar)
        countryFlag.add(R.drawable.ic_united_states_of_america)

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val converView = inflater.inflate(R.layout.list_view, null)

        builder.setView(converView)
        builder.setTitle("Languages")
        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })

        val listView = converView.findViewById<ListView>(R.id.list_view)
        val data: MutableList<Map<String, Any?>> = ArrayList()
        for (i in 0..1) {
            val datum: MutableMap<String, Any?> = HashMap(2)
            datum["name"] = countryTitle[i]
            datum["thumbnail"] = countryFlag[i]
            data.add(datum)
        }

        val simpleAdapter = SimpleAdapter(requireContext(), data, R.layout.language_array_adapter, arrayOf("thumbnail", "name"), intArrayOf(R.id.img_country_flag, R.id.tv_country_name))
        listView.adapter = simpleAdapter
        listView.setOnItemClickListener { _, _, i, _ ->
            when(i){
                0 -> {
                    changeLanguage("Myanmar")
                }
                1 -> {
                   changeLanguage("English")
                }
            }
        }
        builder.show()
    }

    private fun changeLanguage(language : String){
        Hawk.put("language", language)
        tv_languages.text = language
        (wordRecyclerView.adapter as CommWordAdapter).notifyDataSetChanged()
    }
}