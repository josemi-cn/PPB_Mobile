package com.example.projectphonebasket.`class`

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product_Command(
    var id : Int,
    var productId : Int,
    var commandId : Int,
    var quantity : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(productId)
        parcel.writeInt(commandId)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product_Command> {
        override fun createFromParcel(parcel: Parcel): Product_Command {
            return Product_Command(parcel)
        }

        override fun newArray(size: Int): Array<Product_Command?> {
            return arrayOfNulls(size)
        }
    }
}
