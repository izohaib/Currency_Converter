package com.example.currency_converter.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.currency_converter.Utils.CurrencyInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.util.Currency
import java.util.Locale

//for the getCountryList(); for the country.json file
data class CurrencyInfo(
    val countryCode: String,
    val countryName: String,
    val currencyCode: String
)


fun getCountryList(context: Context): List<CurrencyInfo> {
    // Step 1: Read the JSON file from assets as text
    val json = context.assets.open("country.json").bufferedReader().use { it.readText() }

    // Step 2: Convert the string into a JSONObject
    val jsonObject = JSONObject(json)

    // Step 3: Create empty list
    val list = mutableListOf<CurrencyInfo>()

    // Step 4: Loop through each key (like "us", "bd", "gb" etc)
    val keys = jsonObject.keys()
    while (keys.hasNext()) {
        val code = keys.next()  // like "us", "bd"
        val obj = jsonObject.getJSONObject(code)

        val name = obj.getString("country")         // "United States"
        val currencyCode = obj.getString("currency_code") // "usd"

        // Step 5: Create object and add to list
        list.add(CurrencyInfo(code, name, currencyCode))
    }

    // Step 6: Return the list
    return list
}

fun getCurrencySymbol(currencyCode: String): String {
    return try {
        val currency = Currency.getInstance(currencyCode.uppercase(Locale.ROOT))
        currency.symbol
    } catch (e: Exception) {
        "" // fallback if symbol not found
    }
}

@Composable
fun systemBarsColor(backgroundColor: Color) {
    val context = LocalContext.current
    // Set system bars color
    SideEffect {
        (context as? Activity)?.window?.statusBarColor = backgroundColor.toArgb()
        (context as? Activity)?.window?.navigationBarColor = backgroundColor.toArgb()
    }
}


//@SuppressLint("ConstantLocale")
//val locale = Locale.getDefault() // Detects user's phone country
//val localeCountryCode = locale.country.lowercase()       // Example: "us"
//val localeCurrencyCode = Currency.getInstance(locale).currencyCode.lowercase()  // Example: "usd"



//fun loadCurrencyInfoo(context: Context): Map<String, CurrencyInfo> {
//    val jsonString = context.assets.open("country.json").bufferedReader().use { it.readText() }
//    val gson = Gson()
//    val type = object : TypeToken<Map<String, CurrencyInfo>>() {}.type
//    return gson.fromJson(jsonString, type)
//}
//
//fun loadCurrencyInfo(context: Context): Map<String, CurrencyInfo> {
//    val json = context.assets.open("country.json").bufferedReader().use { it.readText() }
//    val jsonObject = JSONObject(json)
//
//    val result = mutableMapOf<String, CurrencyInfo>()
//    val keys = jsonObject.keys()
//    while (keys.hasNext()) {
//        val key = keys.next() // example: "us"
//        val obj = jsonObject.getJSONObject(key)
//
//        val country = obj.getString("country")
//        val currencyCode = obj.getString("currencyCode")
//        val currency = obj.getString("currency")
//
//        result[currencyCode.lowercase()] = CurrencyInfo(
//            country = country,
//            currencyCode = currencyCode,
//            currency = currency
//        )
//    }
//    return result
//}
