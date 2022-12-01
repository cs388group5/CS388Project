package com.example.nightlifeapp.fragments

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nightlifeapp.Crime
import com.example.nightlifeapp.CrimeAdapter
import com.example.nightlifeapp.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


class HomeFragment : Fragment() {

    lateinit var rvCrimes: RecyclerView

    lateinit var adapter:CrimeAdapter

    lateinit var swipeContainer: SwipeRefreshLayout

    var allCrimes: MutableList<Crime> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            allCrimes.clear()
            queryPosts()
            swipeContainer.isRefreshing = false
        }

        rvCrimes=view.findViewById(R.id.rvCrimes)

        adapter= CrimeAdapter(requireContext(),allCrimes)
        rvCrimes.adapter=adapter

        rvCrimes.layoutManager= LinearLayoutManager(requireContext())

        queryPosts()

    }

    //Query for all posts in server
    open fun queryPosts() {

        //specify which class to query
        val query: ParseQuery<Crime> = ParseQuery.getQuery(Crime::class.java)

        //find all post objects
        query.include(Crime.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        query.findInBackground(object : FindCallback<Crime> {
            override fun done(posts: MutableList<Crime>?, e: ParseException?) {
                if(e!=null){
                    Log.e(TAG,"Error fetching posts")
                }else {
                    if(posts!=null){
                        for(post in posts){
                            Log.i(TAG,"Post: "+post.getDescription() + ", username: "+post.getUser()?.username)
                        }

                        allCrimes.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    companion object{
        val TAG="FeedFragment"
    }


}