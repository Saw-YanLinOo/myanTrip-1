package com.vmyan.myantrip.module

import com.vmyan.myantrip.data.*
import com.vmyan.myantrip.ui.viewmodel.*
import org.koin.dsl.module


val addNewTripModule = module {
    single {
        AddNewTripViewModel(AddNewTripRepositoryImpl())
    }
}

val blogTripModule = module {
    single {
        BlogViewModel(BlogRepositoryImpl())
    }
}

val homeModule = module {
    single {
        HomeViewModel(HomeRepositoryImpl())
    }
}

val loginModule = module {
    single {
        LoginViewModel(LoginRepositoryImpl())
    }
}

val pastTripModule = module {
    single {
        PastTripViewModel(PastTripRepositoryImpl())
    }
}

val placeByCategoryModule = module {
    single {
        PlaceByCategoryViewModel(PlaceByCategoryRepositoryImpl())
    }
}

val placeDetailsModule = module {
    single {
        PlaceDetailsViewModel(PlaceDetailsRepositoryImpl())
    }
}

val searchPlaceModule = module {
    single {
        SearchPlaceViewModel(SearchPlaceRepositoryImpl())
    }
}

val upComingTripModule = module {
    single {
        UpComingTripViewModel(UpComingTripRepositoryImpl())
    }
}