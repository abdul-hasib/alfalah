package org.apps.alfalahindia.Managers;


import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DatetimeManager implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    Context context;
    private EditText editText;
    private Calendar calendar;

    public DatetimeManager(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.calendar = Calendar.getInstance();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(context, this, year, month, day).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.editText.setText(day + "/" + month + "/" + year);
    }
}
