package com.example.admin.news

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.admin.news.Adapter.FeedAdapter
import com.example.admin.news.Common.HTTPDataHandler
import com.example.admin.news.Model.RSSObject
import com.google.gson.Gson
import  kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var RSS_link = "https://lenta.ru/rss/news"
    private val RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar1.visibility = -1

        toolbar.title = "News from lenta.ru"
        setSupportActionBar(toolbar)

        var linearLayoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        RecycleView.layoutManager = linearLayoutManager

        loadRSS()
    }

    private fun loadRSS() {
        val loadRSSAsync = object:AsyncTask<String,String,String>(){
            override fun onPreExecute() {
                mDialog.setMessage("Downloading...")
                mDialog.show()
            }

            override fun doInBackground(vararg p0: String?): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(p0[0])
                return  result
            }

            override fun onPostExecute(result: String?) {
              mDialog.dismiss()
                val gson = Gson()
                var rssObject:RSSObject
                rssObject = gson.fromJson<RSSObject>(result,RSSObject::class.java!!)
                val adapter = FeedAdapter(rssObject,baseContext)
                RecycleView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            internal var mDialog = ProgressDialog(this@MainActivity)
        }


        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.book) {
            RSS_link = "https://lenta.ru/rss/news"
            loadRSS()
        }
        if(item.itemId == R.id.seven) {
            RSS_link = "https://lenta.ru/rss/top7"
            loadRSS()
        }
        if(item.itemId == R.id.big_menu) {
            toolbar1.isEnabled
            toolbar1.visibility = -1*toolbar1.visibility


        }
        return true
    }
}
