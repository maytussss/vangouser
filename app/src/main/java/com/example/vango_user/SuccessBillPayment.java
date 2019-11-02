package com.example.vango_user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessBillPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_bill_payment);
        getSupportActionBar().hide();

        ImageView billIcon =  (ImageView)this.findViewById(R.id.correcticon);
        billIcon.setImageResource(R.drawable.check);

        TextView fromTXT = (TextView)this.findViewById(R.id.from_read_text);
        TextView toTXT = (TextView)this.findViewById(R.id.to_read_text);
        TextView priceTXT = (TextView)this.findViewById(R.id.price_read_text);

        // Button
        // Decline Button
        findViewById(R.id.goticketbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SuccessBillPayment.this, TicketActivity.class);
                        startActivity(intent);
                    }
                }
        );
        // Accept Button
        findViewById(R.id.gomainbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SuccessBillPayment.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
    public void onBackPressed() {
        Intent intent = new Intent(SuccessBillPayment.this, MainActivity.class);
        startActivity(intent);
    }
}
