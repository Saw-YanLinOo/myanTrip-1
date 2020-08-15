package com.vmyan.myantrip

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.vmyan.myantrip.data.*
import com.vmyan.myantrip.ui.viewmodel.*
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MyanTripApp : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@MyanTripApp))
        bind<LoginRepository>() with singleton { LoginRepositoryImpl() }
        bind<HomeRepository>() with singleton { HomeRepositoryImpl() }
        bind<PlaceByCategoryRepository>() with singleton { PlaceByCategoryRepositoryImpl() }
        bind<SearchPlaceRepository>() with singleton { SearchPlaceRepositoryImpl() }
        bind<PlaceDetailsRepository>() with singleton { PlaceDetailsRepositoryImpl() }
        bind<BlogRepository>() with singleton { BlogRepositoryImpl() }
        bind()  from provider { LoginVMFactory(instance()) }
        bind() from provider { HomeVMFactory(instance()) }
        bind() from provider { PlaceByCategoryVMFactory(instance()) }
        bind() from provider { SearchPlaceVMFactory(instance()) }
        bind() from provider { PlaceDetailsVMFactory(instance()) }
        bind() from provider { BlogVMFactory(instance()) }
        Hawk.init(applicationContext).build()

    }
}