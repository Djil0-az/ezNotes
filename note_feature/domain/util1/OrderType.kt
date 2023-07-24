package com.djilocodes.eznotes.note_feature.domain.util1
// order type is dsc or asc
sealed class OrderType{
    object  Ascending: OrderType()
    object  Descending: OrderType()
}
