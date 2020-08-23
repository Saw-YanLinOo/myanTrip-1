package com.vmyan.myantrip.data

import com.vmyan.myantrip.model.Backpack
import com.vmyan.myantrip.utils.Resource

interface TripBackPackRepository {
    suspend fun addBackpack(tripId: String, name: String, count: Int) : Resource<String>
    suspend fun getBackpackList(tripId: String) : Resource<MutableList<Backpack>>
    suspend fun updateCheck(tripId: String, bid: String, status: Boolean) : Resource<String>
}