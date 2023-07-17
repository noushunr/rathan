package com.example.rathaanelectronics.Utils

import android.annotation.SuppressLint
import android.os.Parcel
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import java.util.*


@SuppressLint("ParcelCreator")
class DateValidatorWeekdays(var disableDaysInWeek: List<Int>) :
    DateValidator {
    private val utc: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    var disable: List<Int>? = null

    //    public static final Creator<DateValidatorWeekdays> CREATOR =
    //            new Creator<DateValidatorWeekdays>() {
    //                @Override
    //                public DateValidatorWeekdays createFromParcel(Parcel source) {
    //                    return new DateValidatorWeekdays();
    //                }
    //
    //                @Override
    //                public DateValidatorWeekdays[] newArray(int size) {
    //                    return new DateValidatorWeekdays[size];
    //                }
    //            };
    override fun isValid(date: Long): Boolean {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE) + 2
        )
        val minDate: Long = calendar.getTimeInMillis()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE) + 14
        )
        val maxDate: Long = calendar.getTimeInMillis()
        utc.setTimeInMillis(date)
        val dayOfWeek: Int = utc.get(Calendar.DAY_OF_WEEK)
        return date >= minDate && !disableDaysInWeek.contains(dayOfWeek - 1) && date < maxDate
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        return if (o !is DateValidatorWeekdays) {
            false
        } else true
    }

    override fun hashCode(): Int {
        val hashedFields = arrayOf<Any>()
        return Arrays.hashCode(hashedFields)
    }
}