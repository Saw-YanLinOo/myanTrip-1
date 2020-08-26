package com.vmyan.myantrip.ui.adapter.hotel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmyan.myantrip.R
import com.vmyan.myantrip.model.hotel.RoomList
import kotlinx.android.synthetic.main.hotel_room_list.view.*

class RoomLIstViewAdapter (private val listener: ItemClickListener, private val items: MutableList<RoomList>) : RecyclerView.Adapter<RoomLIstViewAdapter.RoomListViewHolder>(){
    interface ItemClickListener{
        fun onItemClick(id :String)
    }
    fun setItems(items: MutableList<RoomList>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hotel_room_list, parent, false)
        return RoomListViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
      return items.size
    }

    override fun onBindViewHolder(holder: RoomListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RoomListViewHolder(
        private val view: View,
        private val listener : ItemClickListener
    ) : RecyclerView.ViewHolder(view)  {
        private lateinit var roomList: RoomList

        fun bind(item : RoomList){
            this.roomList=item
            view.roomStatus.text=item.roomType
            view.roomPrice.text=item.roomPrice.toString()

            view.llRoomWifi.visibility = View.GONE
            if (item.roomWifi){
                view.llRoomWifi.visibility=View.VISIBLE
            }
            view.llroomAirCon.visibility=View.GONE
            if (item.roomAirCon){
                view.llroomAirCon.visibility=View.VISIBLE
            }
            view.llRoomWithToilet.visibility=View.GONE
            if (item.roomWithToilet){
                view.llRoomWithToilet.visibility=View.VISIBLE
            }


           view.roomReserve.setOnClickListener {
               listener.onItemClick(item.roomId)
           }


        }



    }

}