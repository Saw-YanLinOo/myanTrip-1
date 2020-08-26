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

val registerModule = module {
    single {
        RegisterViewModel(RegisterRepositoryImpl())
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

val tripPlanModule = module {
    single {
        TripPlanViewModel(TripPlanRepositoryImpl())
    }
}

val addPlanModule = module {
    factory {
        AddPlanViewModel(AddPlanRepositoryImpl())
    }
}

val profileModule = module {
    single {
        ProfileViewModel(ProfileRepositoryImpl())
    }
}

val uploadPostModule = module{
    single {
        UploadViewModel(UploadPostRepositoryImpl())
    }
}

val backPackModule = module {
    single {
        TripBackPackViewModel(TripBackPackRepositoryImpl())
    }
}

val teamMateModule = module {
    single {
        TripPlanUserViewModel(TripPlanUserRepositoryImpl())
    }
}

val communicationModule = module {
    single {
        CommunicationViewModel(CommunicationRepositoryImpl())
    }
}
val communicationDetailModule = module {
    single {
        CommunicationDetailViewModel(CommunicationDetailRepoImpl())
    }
}

val commentModule = module{
    single {
        CommentViewModel(CommentRepositoryImpl())
    }
}

val emergencyModule = module {
    single {
        EmergencyViewModel(EmergencyRepositoryImpl())
    }
}

val bookingTicketModule = module {
    factory {
        BookingTicketViewModel(BookingTicketRepositoryImpl())
    }
}