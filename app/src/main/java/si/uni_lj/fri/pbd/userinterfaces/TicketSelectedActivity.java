package si.uni_lj.fri.pbd.userinterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TicketSelectedActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int THIRD_ACTIVITY_REQUEST_CODE = 1;
    TextView destination;
    int button = -1;
    int price = 0;
    int add1 = 0, add2 = 0, add3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selected);

        Intent intent = getIntent();
        String dest = intent.getStringExtra("key");
        //int id = intent.getIntExtra("countryID", 0);


        ConstraintLayout con = (ConstraintLayout) findViewById(R.id.my_layout);
        destination = (TextView) findViewById(R.id.destination);
        destination.setText(dest);

        // set background based on id
        if(dest.contains("Barcelona"))
            con.setBackgroundResource(R.drawable.barca);
        else if(dest.contains("Paris"))
            con.setBackgroundResource(R.drawable.paris);
        else if(dest.contains("Madrid"))
            con.setBackgroundResource(R.drawable.madrid);
        else if(dest.contains("London"))
            con.setBackgroundResource(R.drawable.london);


        chooseDate();



        setContentView(con);

    }

    private void chooseDate(){
        final Calendar myCalendar = Calendar.getInstance();

        Button imageButton= (Button) findViewById(R.id.for_date);
        Button imageButton2= (Button) findViewById(R.id.for_date2);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(imageButton, myCalendar, imageButton2);
            }

        };

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                button = 1;
                new DatePickerDialog(TicketSelectedActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button = 2;
                // TODO Auto-generated method stub
                new DatePickerDialog(TicketSelectedActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    @SuppressLint("SetTextI18n")
    private void updateLabel(Button imageButton, Calendar myCalendar, Button imageButton2) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if(button == 1) {
            TextView textField = (TextView) findViewById(R.id.date_info);
            textField.setText("Departure Date: " + sdf.format(myCalendar.getTime()));
        }else if (button == 2){
            TextView textField = (TextView) findViewById(R.id.date_info2);
            textField.setText("Return Date: " + sdf.format(myCalendar.getTime()));
        }
    }

    public void toAddPassengers(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        Intent myIntent = new Intent(view.getContext(), CustomerActivity.class);
        //Toast.makeText(this, radioButton.getText(), Toast.LENGTH_LONG).show();
        myIntent.putExtra("radioText", radioButton.getText()); //Optional parameters
        //myIntent.putExtra("countryID", position); //Optional parameters
        startActivityForResult(myIntent, SECOND_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                price = price + data.getIntExtra("price", -1);

                String type = data.getStringExtra("type");
                if(type.equals("children")) {
                    add1 = add1 + 1;
                    TextView type1 = (TextView) findViewById(R.id.type1);
                    type1.setText(String.format("children: %dx\n (free)", add1));
                }
                else if(type.equals("teen")) {
                    add2 = add2 + 1;
                    TextView type2 = (TextView) findViewById(R.id.type2);
                    type2.setText(String.format("Teen: %dx\n (40 €)", add2));
                }
                else if(type.equals("adult")) {
                    add3 = add3 + 1;
                    TextView type3 = (TextView) findViewById(R.id.type3);
                    type3.setText(String.format("Adult: %dx\n (80 €)", add3));
                }




                TextView sum = (TextView) findViewById(R.id.sum);
                sum.setText(String.format("Total Sum: %d €", price));
            }
        }
    }

    public void toBuy(View view) {
        Intent myIntent = new Intent(view.getContext(), CardInfoActivity.class);
        myIntent.putExtra("money", price); //Optional parameters
        startActivityForResult(myIntent, THIRD_ACTIVITY_REQUEST_CODE);
    }

    public void oneWay(View view) {
        Button roundTrip = (Button) findViewById(R.id.button2);
        TextView info = (TextView) findViewById(R.id.date_info2);
        Button infoBtn = (Button) findViewById(R.id.for_date2);
        roundTrip.setEnabled(true);
        view.setEnabled(false);

        info.setVisibility(View.INVISIBLE);
        infoBtn.setVisibility(View.INVISIBLE);
    }

    public void roundTrip(View view) {
        Button oneWay = (Button) findViewById(R.id.button1);
        TextView info = (TextView) findViewById(R.id.date_info2);
        Button infoBtn = (Button) findViewById(R.id.for_date2);
        oneWay.setEnabled(true);
        view.setEnabled(false);

        info.setVisibility(View.VISIBLE);
        infoBtn.setVisibility(View.VISIBLE);
    }
}