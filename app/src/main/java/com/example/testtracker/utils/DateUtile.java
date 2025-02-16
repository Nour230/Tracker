package com.example.testtracker.utils;

import android.content.Context;
import android.os.Parcel;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.testtracker.view.interfaces.OnDateSelectedListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtile {
    OnDateSelectedListener listener;

    public static void showDatePicker(FragmentManager fragmentManager, Context context, OnDateSelectedListener listener) {
        // Get the current date and time in the Cairo timezone
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));

        // Set the start of the range to today (in UTC milliseconds)
        long startOfRange = calendar.getTimeInMillis();

        // Set the end of the range to 7 days from today (in UTC milliseconds)
        calendar.add(Calendar.DAY_OF_YEAR, 6); // Add 7 days to the current date
        long endOfRange = calendar.getTimeInMillis();

        // Create a calendar constraint to allow only dates from today to 7 days from today
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(startOfRange); // Start from today
        constraintsBuilder.setEnd(endOfRange); // End at 7 days from today
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= startOfRange && date <= endOfRange; // Only allow dates within the range
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
            }
        });

        // Create the date picker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a day (today to 7 days from now)")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(startOfRange) // Default selection: today (in UTC milliseconds)
                .build();

        // Show the date picker
        datePicker.show(fragmentManager, "DATE_PICKER");

        // Handle the selected date
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert the selected date to a readable format
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            selectedCalendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = format.format(selectedCalendar.getTime());
            String formattedDate = getFormattedDate(selectedDate);

            // Notify the user
            Toast.makeText(context, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Call the listener with the selected date
            if (listener != null) {
                listener.onDateSelected(formattedDate);
            }
        });
    }


    public static String getFormattedDate(String olddate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = inputFormat.parse(olddate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}