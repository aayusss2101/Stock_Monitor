package com.seedbx.stockmonitor

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WishlistActivity : AppCompatActivity() {

    /** A [MutableList]<[StockData]> object which stores the list of StockData objects for which field addToWishlist==true */
    private lateinit var wishlist:MutableList<StockData>

    /**
     * Connects the wishlist to wishlistRecyclerView
     *
     * @param wishlist A [MutableList]<[StockData]> which stores the list of StockData objects for which field addToWishlist==true
     */
    private fun connectWithRecyclerView(wishlist: MutableList<StockData>){
        val wishlistAdapter=WishlistAdapter(this,this,R.layout.wishlist_record,wishlist)
        val wishlistRecyclerView=findViewById<RecyclerView>(R.id.wishlistRecyclerView)
        val itemTouchHelper=ItemTouchHelper(SwipeToDeleteCallback(0,ItemTouchHelper.RIGHT,wishlistAdapter))
        wishlistRecyclerView.layoutManager=LinearLayoutManager(this)
        wishlistRecyclerView.adapter=wishlistAdapter
        itemTouchHelper.attachToRecyclerView(wishlistRecyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        wishlist=DatabaseHelper.getWishlist().toMutableList()

        connectWithRecyclerView(wishlist)
    }
}

class SwipeToDeleteCallback(dragDirs:Int,swipeDirs:Int,var wishlistAdapter: WishlistAdapter):ItemTouchHelper.SimpleCallback(dragDirs,swipeDirs){

    /** A [ColorDrawable] drawn when item is swiped */
    var background:ColorDrawable = ColorDrawable(Color.RED)

    /**
     * Returns false specifying that move operation is disabled in this recyclerView
     *
     * @param recyclerView A [RecyclerView]
     * @param viewHolder A [RecyclerView.ViewHolder]
     * @param target A [RecyclerView.ViewHolder]
     * @return A [Boolean] which is equal to false
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    /**
     * Removes the current swiped item from the RecyclerView when it is Right Swiped
     *
     * @param viewHolder A [RecyclerView.ViewHolder] which was swiped
     * @param direction A [Int] specifying in which direction the swipe was done
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(direction){
            ItemTouchHelper.RIGHT->{
                val pos=viewHolder.adapterPosition
                wishlistAdapter.removeItem(pos)
            }
        }
    }

    /**
     * Draws the background on c if the item is swiped in the right direction
     *
     * @param c A [Canvas] which RecyclerView is drawing its children in
     * @param recyclerView A [RecyclerView] to which ItemTouchHelper is attached to
     * @param viewHolder A [RecyclerView.ViewHolder] which is being interacted by the user
     * @param dX A [Float] specifying the amount of horizontal displacement
     * @param dY A [Float] specifying the amount of vertical displacement
     * @param actionState A [Int] specifying the type of user interaction with viewHolder
     * @param isCurrentlyActive A [Boolean] specifying whether user is currently interacting with it or not
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView=viewHolder.itemView
        val backgroundOffset=20
        if(dX>0)
            background.setBounds(itemView.left,itemView.top,itemView.left+dX.toInt()+backgroundOffset,itemView.bottom)
        else
            background.setBounds(0,0,0,0)
        background.draw(c)
    }
}