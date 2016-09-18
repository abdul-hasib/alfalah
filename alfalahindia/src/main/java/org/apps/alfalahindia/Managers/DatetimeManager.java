package org.apps.alfalahindia.Managers;


import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apps.alfalahindia.Util.DatetimeUtil;

import java.util.Calendar;
import java.util.Date;

public class DatetimeManager implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    Context context;
    private EditText editText;
    private Calendar calendar;

    public DatetimeManager(EditText editText, Context context) {
        this.context = context;
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            calendar = Calendar.getInstance();

            String textValue = editText.getText().toString();
            if (!textValue.isEmpty()) {
                Date dt = DatetimeUtil.parseDate(textValue);
                if (dt != null) {
                    calendar.setTimeInMillis(dt.getTime());
                }
            }

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(context, this, year, month, day).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        StringBuilder date = new StringBuilder()
                .append(day).append("/")
                .append(month + 1).append("/")
                .append(year);

        Log.d("ALIF", date.toString());
        this.editText.setText(date.toString());
    }
}
