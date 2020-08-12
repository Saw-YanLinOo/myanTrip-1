package com.vmyan.myantrip

import android.app.Application
import com.vmyan.myantrip.data.*
import com.vmyan.myantrip.ui.viewmodel.HomeVMFactory
import com.vmyan.myantrip.ui.viewmodel.PlaceByCategoryVMFactory
import com.vmyan.myantrip.ui.viewmodel.PlaceDetailsVMFactory
import com.vmyan.myantrip.ui.viewmodel.SearchPlaceVMFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MyanTripApp : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@MyanTripApp))
        bind<HomeRepository>() with singleton { HomeRepositoryImpl() }
        bind<PlaceByCategoryRepository>() with singleton { PlaceByCategoryRepositoryImpl() }
        bind<SearchPlaceRepository>() with singleton { SearchPlaceRepositoryImpl() }
        bind<PlaceDetailsRepository>() with singleton { PlaceDetailsRepositoryImpl() }
        bind() from provider { HomeVMFactory(instance()) }
        bind() from provider { PlaceByCategoryVMFactory(instance()) }
        bind() from provider { SearchPlaceVMFactory(instance()) }
        bind() from provider { PlaceDetailsVMFactory(instance()) }
    }

}