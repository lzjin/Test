package com.danqiu.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.danqiu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by Administrator on 2018/9/30.
 * 拍照识别卡号
 */

public class CardNoAct extends BaseActivity {
    @BindView(R.id.bt_card)
    Button btCard;
    private int MY_SCAN_REQUEST_CODE=10001;

    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_card_no);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_card)
    public void onViewClicked() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

// customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

// MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String result;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                result = "得到卡号: " + scanResult.getRedactedCardNumber() + "\n";

                if (scanResult.isExpiryValid()) {
                    result += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    result += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    result += "Postal Code: " + scanResult.postalCode + "\n";

                }
            } else {
                result = "取消";
            }
            Log.e("test","-------------"+result);

        }


    }
}
