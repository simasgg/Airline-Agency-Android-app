package si.uni_lj.fri.pbd.userinterfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomerActivity extends AppCompatActivity {

    String radioID;
    double price = 0;
    int year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Intent intent = getIntent();
        radioID = intent.getStringExtra("radioText");
        //Toast.makeText(this, radioID, Toast.LENGTH_LONG).show();

        TextView infoText = (TextView) findViewById(R.id.info);
        if(radioID.contains("Economy"))
            infoText.setText("Buying Economy Class Ticket");
        else if(radioID.contains("Business"))
            infoText.setText("Buying Business Class Ticket");
        else if(radioID.contains("First"))
            infoText.setText("Buying First Class Ticket");

        chooseDate();
    }

    private void chooseDate(){
        TextView tvw=(TextView)findViewById(R.id.date_selected);
        DatePicker picker=(DatePicker)findViewById(R.id.date_picker);
        Button btnGet=(Button)findViewById(R.id.date);
        Button select=(Button)findViewById(R.id.button5);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select.setEnabled(true);
                //tvw.setText("Birth of date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
                year = 2021 - picker.getYear();
                if(year<2) {
                    tvw.setText("Type of ticket: children \n" + "Final price: 0 €");
                    price = 0;
                }
                else if (year<12) {
                    price=0;
                    if(radioID.contains("Economy"))
                        price = 40;
                    else if(radioID.contains("Business"))
                        price = 40*1.2;
                    else if(radioID.contains("First"))
                        price = 40*1.5;

                    tvw.setText(String.format("Type of ticket: teen \nFinal price: %d €", (int)price));
                }
                else {
                    price=0;
                    if(radioID.contains("Economy"))
                        price = 80;
                    else if(radioID.contains("Business"))
                        price = 80*1.2;
                    else if(radioID.contains("First"))
                        price = 80*1.5;

                    tvw.setText(String.format("Type of ticket: adult \nFinal price: %d €", (int)price));
                }
            }
        });

    }

    public void toSelect(View view) {
        Intent intent = new Intent();
        intent.putExtra("price", (int)price);
        if(year<2) {
            intent.putExtra("type", "children");
        }
        else if (year<12) {
            intent.putExtra("type", "teen");
        }
        else {
            intent.putExtra("type", "adult");
        }
        //Toast.makeText(this, (int) price, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void toCancel(View view) {
        Intent intent = new Intent();
        intent.putExtra("price", (int)price);
        intent.putExtra("type", "none");
        setResult(RESULT_OK, intent);
        finish();
    }
}