package com.vmyan.myantrip

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyanTripApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()

        startKoin {
            androidContext(this@MyanTripApp)
            modules(listOf(
                addNewTripModule,
                blogTripModule,
                homeModule,
                loginModule,
                registerModule,
                pastTripModule,
                placeByCategoryModule,
                placeDetailsModule,
                searchPlaceModule,
                upComingTripModule,
                profileModule,
                uploadPostModule,
                communicationModule,
                communicationDetailModule,
                tripPlanModule,
                addPlanModule,
                backPackModule,
                teamMateModule,
                commentModule,
                teamMateModule,
                emergencyModule
            ))
        }
    }

}