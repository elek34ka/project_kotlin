package com.example.admin.news.Adapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.admin.news.Interface.ItemClickListener
import com.example.admin.news.Model.RSSObject
import com.example.admin.news.R


class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
{
    var txtTitle: TextView
    var txtPubdate:TextView
    var txtContent:TextView

    private var itemClickListener: ItemClickListener?=null
    init{
        txtTitle = itemView.findViewById(R.id.txtTitle)// as TextView
        txtContent = itemView.findViewById(R.id.txtContent)// as TextView
        txtPubdate = itemView.findViewById(R.id.txtPubdate)// as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?){
        itemClickListener!!.onClick(v,adapterPosition,false)
    }

    override fun onLongClick(v: View?):Boolean {
        itemClickListener!!.onClick(v,adapterPosition,true)
        return true
    }
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext:Context):RecyclerView.Adapter<FeedViewHolder>()
{

    private var inflater:LayoutInflater

    init{
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row,parent,false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubdate.text = rssObject.items[position].pubData
        holder.txtContent.text = rssObject.items[position].content

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->

            if(!isLongClick){
                val browserIntent = Intent(Intent.ACTION_VIEW,Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)
            }else{
                
            }
        })


           }

    override fun getItemCount(): Int {
     return rssObject.items.size
    }

}