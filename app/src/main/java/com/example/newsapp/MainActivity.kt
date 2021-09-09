package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), clickListener {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var mAdapter:NewsAdapter

    var news_url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=0bfd101369b949c4923b33baae521307"

    override fun onCreate(savedInstanceState: Bundle?) {

        val actionBar = supportActionBar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1 -> {
                                actionBar!!.title = "Business"
                                fetchData("&category=business")
                              }

                R.id.item2 -> {
                                actionBar!!.title = "Entertainment"
                                fetchData("&category=entertainment")
                              }

                R.id.item3 -> {
                                actionBar!!.title = "General"
                                fetchData("&category=general")
                              }

                R.id.item4 -> {
                                actionBar!!.title = "Health"
                                fetchData("&category=health")
                              }

                R.id.item5 -> {
                                actionBar!!.title = "Science"
                                fetchData("&category=science")
                              }

                R.id.item6 -> {
                                actionBar!!.title = "Sports"
                                fetchData("&category=sports")
                              }

                R.id.item7 -> {
                                actionBar!!.title = "Technology"
                                fetchData("&category=technology")
                              }
            }
            true
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchData("")

        mAdapter = NewsAdapter(this, this)

        recyclerView.adapter = mAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun fetchData(query: String) {
        val queue = Volley.newRequestQueue(this)
        val url = news_url+query
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<NewsDataClass>()
                for(i in 0 until  newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = NewsDataClass(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,
                    "Something went wrong, please try again later", Toast.LENGTH_LONG)
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        queue.add(getRequest)
    }

    override fun onItemsClicked(items: NewsDataClass) {
        val builder =  CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(items.url));
    }


}