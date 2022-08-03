package com.example.androidtask.common

object Constants {

    const  val DEFAULT_IMAGE: String="https://cdn-icons-png.flaticon.com/512/149/149071.png"

    /************ Complex Preference ***********/
    const val PREFERENCES_NAME="complex_preferences"

    /************ Base Url ***********/
    const val BASE_URL="https://dummyjson.com/"

    /************ User Flag ***********/
    const val IS_LOGIN = "IS_LOGIN"
    const val IS_FIRST_TIME= "IS_FIRST_TIME"
    const val USER_DATA = "USER_DATA"
    const val USER_LANG = "USER_LANG"

    // you can put any unique id here, but because I am using Navigation Component I prefer to put it as
    // the fragment id.
//    const val HOME_ITEM = AppIds.homeFragment
//    const val FAV_ITEM = AppIds.favouriteFragment
//    const val CART_ITEM = AppIds.cartFragment
//    const val NOTIFICATION_ITEM = AppIds.notificationFragment
//    const val ACCOUNT_ITEM = AppIds.profileFragment

    const val DONE_MESSAGE_VIEW="\uD83D\uDE0D"

}


typealias AppR=com.example.androidtask.R
typealias AppColor=com.example.androidtask.R.color
typealias AppStrings=com.example.androidtask.R.string
typealias AppDrawables=com.example.androidtask.R.drawable
typealias AppIds=com.example.androidtask.R.id
const val TAG="MYAPPLICATIONTASK"