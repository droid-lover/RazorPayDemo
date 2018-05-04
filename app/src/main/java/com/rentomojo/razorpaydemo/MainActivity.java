package com.rentomojo.razorpaydemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.CheckoutActivity;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {


    @BindView(R.id.payment_value_et)
    EditText mPaymentValueEditText;

    @BindView(R.id.confirm_button)
    Button mConfirmButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeViews();

    }

    private void initializeViews() {


    }

    @OnClick(R.id.confirm_button)
    public void confirmButtonClicked() {
        if (TextUtils.isEmpty(mPaymentValueEditText.getText().toString())) {
            Toast.makeText(MainActivity.this, "Please enter some value to pay", Toast.LENGTH_LONG).show();
            return;
        }

        startPaymentProcess();
    }

    private void startPaymentProcess() {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            String payment = mPaymentValueEditText.getText().toString();

            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);

//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "sikander@gkmit.co");
//            preFill.put("contact", "9680224241");
//
//            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment successfully done! " + razorpayPaymentID, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, " Error in Payment " + s, Toast.LENGTH_SHORT).show();
    }
}
