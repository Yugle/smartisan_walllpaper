package com.yugle.smartisanwallpaper.data

import android.graphics.drawable.Drawable

data class BaseItem(
    val itemPrefixIcon: Drawable? = null,
    val itemTitle: String,
    var itemValue: String? = null,
    val itemUnit: String? = null,
    val itemSuffixIcon: Drawable? = null
)