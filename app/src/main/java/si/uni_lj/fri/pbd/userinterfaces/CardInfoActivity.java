package si.uni_lj.fri.pbd.userinterfaces;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CardInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        Intent intent = getIntent();
        int money = intent.getIntExtra("money", -1);
        TextView sum = (TextView) findViewById(R.id.to_pay);
        sum.setText(String.format("You have to pay: %d €", money));
    }


    public void toPay(View view) {
        Toast.makeText(this, "Transaction was successful", Toast.LENGTH_LONG).show();

        Intent returnBtn = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(returnBtn);
    }


}