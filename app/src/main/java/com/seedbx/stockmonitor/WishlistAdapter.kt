package com.seedbx.stockmonitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class WishlistAdapter(val context: Context, private val activity: WishlistActivity, private val resource:Int, private val wishlist:MutableList<StockData>):
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>(),AddToWishlistClickListener.OnResponse{

    /** A [LayoutInflater] used to inflate the layout */
    private val inflater = LayoutInflater.from(context)

    /** A [StockData] which is the StockData object recently deleted from wishlist */
    private lateinit var recentlyDeletedItem:StockData

    /** A [Int] which is the position of the recently deleted item from wishlist */
    private var recentlyDeletedPosition by Delegates.notNull<Int>()

    class WishlistViewHolder(v: View): RecyclerView.ViewHolder(v){

        /** A [TextView] used to store the field companyName */
        val companyName: TextView =v.findViewById(R.id.wishlist_companyName)

        /** A [TextView] used to store the field stockExchange */
        val stockExchange: TextView =v.findViewById(R.id.wishlist_stockExchange)

        /** A [TextView] used to store the field tickerSymbol */
        val tickerSymbol: TextView =v.findViewById(R.id.wishlist_tickerSymbol)
    }

    /**
     * Deletes StockData object at wishlist.at(position)
     *
     * @param position A [Int] representing the position to delete from wishlist
     */
    private fun remove(position:Int){
        recentlyDeletedItem= wishlist[position]
        recentlyDeletedPosition=position
        wishlist.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Adds back recentlyDeletedItem to wishlist
     */
    private fun undo(){
        wishlist.add(recentlyDeletedPosition,recentlyDeletedItem)
        notifyItemInserted(recentlyDeletedPosition)
    }

    /**
     * Undoes the recent delete operation
     */
    private fun undoDelete(){
        undo()
        val addToWishlistClickListener=AddToWishlistClickListener(context)
        addToWishlistClickListener.updateWishlistValue(recentlyDeletedItem, this)
    }

    /**
     * Shows the Undo Snack Bar
     */
    private fun showUndoSnackBar(){
        val view: ConstraintLayout =activity.findViewById(R.id.wishlistConstraintLayout)
        val snackBar=
            Snackbar.make(view,"${recentlyDeletedItem._id} removed from wishlist", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo"){
            undoDelete()
        }
        snackBar.show()
    }

    /**
     * Removes the StockData object at wishlist.at(wishlist) from RecyclerView
     *
     * @param position A [Int] which represents the position of item to be deleted
     */
    fun removeItem(position: Int){
        remove(position)
        val addToWishlistClickListener=AddToWishlistClickListener(context)
        addToWishlistClickListener.updateWishlistValue(recentlyDeletedItem,this)
    }


    /**
     * Creates the WishlistViewHolder with parent as 'parent'
     *
     * @param parent A [ViewGroup] which acts as the parent view for the FinancialViewHolder
     * @param viewType A [Int] specifying the type of the view for the new view
     * @return A [WishlistViewHolder] which is the newly created view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view=inflater.inflate(resource,parent,false)
        return WishlistViewHolder(view)
    }

    /**
     * Binds data to the WishlistViewHolder at position 'position' in RecyclerView
     *
     * @param wishlistViewHolder A [WishlistViewHolder] which is the view holder for the item in the RecyclerView
     * @param position A [Int] specifying the position of the item in the RecyclerView
     */
    override fun onBindViewHolder(wishlistViewHolder: WishlistViewHolder, position: Int) {

        val currentStock=wishlist[position]
        wishlistViewHolder.companyName.text=currentStock.companyName
        wishlistViewHolder.stockExchange.text=currentStock.stockExchange
        wishlistViewHolder.tickerSymbol.text=currentStock.tickerSymbol

        wishlistViewHolder.itemView.setOnClickListener {
            StockDataClickListener().onClick(
                context,
                currentStock
            )
        }

    }

    /**
     * Returns the number of items in wishlist
     *
     * @return A [Int] equal to the size of wishlist
     */
    override fun getItemCount(): Int {
        return wishlist.size
    }

    /**
     * Handles the successful completion of request to modify the field addToWishlist
     *
     * @param currentStock A [StockData] whose field was modified
     */
    override fun onResponseComplete(currentStock: StockData) {
        currentStock.addToWishlist = AddToWishlistClickListener.not(currentStock.addToWishlist)
        DatabaseHelper.updateWishlist(currentStock._id,currentStock.addToWishlist)
        if(!currentStock.addToWishlist)
            showUndoSnackBar()
    }

    /**
     * Handles the unsuccessful completion of request to modify the field addToWishlist
     *
     * @param error A [VolleyError], the error that occurred
     * @param addToWishlist A [Boolean] that represents the before modification value of field addToWishlist
     */
    override fun onResponseError(error: VolleyError, addToWishlist: Boolean) {
        if(addToWishlist)
            undo()
        else
            remove(recentlyDeletedPosition)
        Toast.makeText(context,"Some Error Occurred", Toast.LENGTH_SHORT).show()
    }

}
