package com.vmyan.myantrip

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.data.*
import com.vmyan.myantrip.ui.viewmodel.*
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind

class MyanTripApp : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@MyanTripApp))
        bind<LoginRepository>() with singleton { LoginRepositoryImpl() }
        bind<HomeRepository>( ) with singleton { HomeRepositoryImpl() }
        bind<PlaceByCategoryRepository>( ) with singleton { PlaceByCategoryRepositoryImpl() }
        bind<SearchPlaceRepository>( ) with singleton { SearchPlaceRepositoryImpl() }
        bind<PlaceDetailsRepository>( ) with singleton { PlaceDetailsRepositoryImpl() }
        bind<BlogRepository>( ) with singleton { BlogRepositoryImpl() }
        bind<AddNewTripRepository>( ) with singleton { AddNewTripRepositoryImpl() }
        bind<UpComingTripRepository>( ) with singleton { UpComingTripRepositoryImpl() }
        bind<PastTripRepository>( ) with singleton { PastTripRepositoryImpl() }
        bind( )  from provider { LoginVMFactory(instance()) }
        bind( ) from provider { HomeVMFactory(instance()) }
        bind( ) from provider { PlaceByCategoryVMFactory(instance()) }
        bind( ) from provider { SearchPlaceVMFactory(instance()) }
        bind( ) from provider { PlaceDetailsVMFactory(instance()) }
        bind( ) from provider { BlogVMFactory(instance()) }
        bind( ) from provider { AddNewTripVMFactory(instance()) }
        bind( ) from provider { UpComingTripVMFactory(instance()) }
        bind( ) from provider { PastTripVMFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
    }

}