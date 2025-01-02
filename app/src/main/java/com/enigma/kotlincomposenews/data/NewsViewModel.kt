package com.enigma.kotlincomposenews.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigma.kotlincomposenews.Constant
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles


    init {
        fetchNewsTopHeadlines()
    }



    fun fetchNewsTopHeadlines(category : String = "GENERAL"){
        val newsApiClient = NewsApiClient(Constant.apiKey)

        val request = TopHeadlinesRequest.Builder().language("en").category(category).build()

        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.forEach {
                    Log.i("ABCD", it.title)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable != null) {
                    Log.i("NewsAPI Response Failed",throwable.localizedMessage)
                }
            }

        })

    }
}