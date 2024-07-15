package com.example.si

sealed class Screen(val route: String) {

    object LoginScreen : Screen(route = Routes.loginScreen)
    object RegisterScreen : Screen(route = Routes.registerScreen)
    object MainScreen: Screen(route = Routes.mainScreen)
    object GlossarioScreen: Screen(route = Routes.glossarioScreen)
    object GerenciarMedScreen: Screen(route = Routes.gerenciarMedScreen)
    object AddMedScreen: Screen(route = Routes.addMedScreen)
    object ListMedScreen: Screen(route = Routes.listMedScreen)
    object EditMedScreen: Screen(route = Routes.editMedScreen)
    object PrescriptionScreen : Screen(route = Routes.prescriptionScreen)
    object CalendarScreen : Screen(route = Routes.calendarScreen)
    object ProfileScreen : Screen(route = Routes.profileScreen)
}