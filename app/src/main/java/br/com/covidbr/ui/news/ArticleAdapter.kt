package br.com.covidbr.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.covidbr.R
import br.com.covidbr.data.news.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(private val articles: List<Article>,
private val onClick: (Article) -> Unit = {}) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bindView(article)
        holder.itemView.setOnClickListener {
            onClick(article)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(article: Article) {
            with(itemView) {
                textViewArticleTitle.text = article.title
                textViewArticleDescription.text = article.description
            }
        }
    }
}