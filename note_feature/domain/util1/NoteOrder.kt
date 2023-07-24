package com.djilocodes.eznotes.note_feature.domain.util1
// order the the notes according to the following properties
// OrderType determines if it is ASC or DSC order
sealed class NoteOrder(val orderType: OrderType){
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)

    // fun to keep note order  but change note order type
    fun copy(orderType: OrderType): NoteOrder{
        return  when(this){
            // return when title order type to asc or dsc
            is Title-> Title(orderType)
            // return Date order type
            is Date-> Date(orderType)
            //return Color order type
            is Color-> Color(orderType)
        }
    }
}
