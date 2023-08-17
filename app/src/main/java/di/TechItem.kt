package di

import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class TechItem(
    val title: String?,
    val img: Int,
    val description: String?,
    var isInFavorites: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(img)
        parcel.writeString(description)
        parcel.writeByte(if (isInFavorites) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TechItem> {
        override fun createFromParcel(parcel: Parcel): TechItem {
            return TechItem(parcel)
        }

        override fun newArray(size: Int): Array<TechItem?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class Parcelize
