package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ljchen on 10/3/16.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    public static final String EXTRA_HOUR =
            "com.bignerdranch.android.criminalintet.hour";
    public static final String EXTRA_MIN =
            "com.bignerdranch.android.criminalintet.min";
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(min);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = mTimePicker.getCurrentHour();
                                int min = mTimePicker.getCurrentMinute();
                                sendResult(Activity.RESULT_OK, hour, min);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, int hour, int min) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MIN, min);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);

    }


}
