package com.adaptation.lixinjia.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * 作者：李忻佳
 * 日期：2017/12/11/011.
 * 说明：银行卡扫描
 */

public class BankCardScanningActivity extends BaseActivity {
    private Button bt;
    private TextView tv;

    private int MY_SCAN_REQUEST_CODE = 100;

    @Override
    public int bindLayout() {
        return R.layout.act_bankcard_scanning;
    }

    @Override
    public void initView(View view) {
        bt = $(R.id.act_bankcard_scanning_bt);
        tv = $(R.id.act_bankcard_scanning_tv);

        mTitle.setCenterText("银行卡扫描");
    }

    @Override
    public void addListener() {
        bt.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == bt){
            Intent scanIntent = new Intent(this, CardIOActivity.class);

//            scanIntent.putExtra(CardIOActivity.EXTRA_NO_CAMERA, false); // 布尔额外。 可选的。 默认为<code> false </ code>。 如果设置，卡将不会被相机扫描。
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // 布尔额外。 可选的。 默认为<code> false </ code>。 如果设置为<code> false </ code>，则不需要过期信息。
            scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, false); // 布尔额外。 可选的。 默认为<code> true </ code>。 如果设置为<code> true </ code>，并且{@link #EXTRA_REQUIRE_EXPIRY}为<code> true </ code>，则会尝试从卡片图像提取到期日期。
//            scanIntent.putExtra(CardIOActivity.EXTRA_UNBLUR_DIGITS, -1); // 整数额外。 可选的。 默认为<code> -1 </ code>（不模糊）。 隐私功能。 在结果图像上有多少卡号码不会模糊。 将其设置为<code> 4 </ code>将会模糊除最后四位之外的所有数字。
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // 布尔特。可选。默认为<代码> false <代码>。如果设置，将提示用户信用卡CVV。
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // 布尔特。可选。默认为<代码> false <代码>。如果设置，用户将被提示为卡帐单邮政编码。
            scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false); //布尔特。可选。默认为<代码> false <代码>。如果设置，邮政编码将只收集数字输入。设置这个如果你知道< a href =“http：/ /恩。维基百科。org /维基/ postal_code”>预计全国的邮政编码</a>只有数字的邮政编码。
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false); //布尔特。可选。默认为<代码> false <代码>。如果设置，将提示用户输入持卡人姓名。
//            scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, false); //布尔特。可选。默认为<代码> false <代码>。如果设置了，这card.io标志将显示相反的贝宝的标志。
//            scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_RESULT, false); //打包的额外包含{@链接信用卡}。数据的意图回到你{@链接Android程序。活动}的{”环节活动# onActivityResult（int，int，意图）}将包含额外的如果ResultCode是{@链接# result_card_info }。
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); //右下角键盘
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); //布尔额外。 可选的。 默认为<code> false </ code>。 从扫描屏幕上移除键盘按钮。 如果扫描不可用，{@link android.app.Activity}结果将为{@link #RESULT_SCAN_NOT_AVAILABLE}。

            // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
            startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);

        }
    }

    @Override
    public void doBusiness() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
//                 myService.setCardNumber( scanResult.cardNumber );
                showToast("银行卡账号是："+scanResult.cardNumber);

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
                tv.setText(resultDisplayStr);

            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
        }
    }
}
