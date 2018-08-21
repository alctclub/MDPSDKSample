package com.alct.mdpsdksample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alct.mdp.MDPLocationCollectionManager;
import com.alct.mdp.callback.OnResultListener;
import com.alct.mdp.model.EnterpriseIdentity;
import com.alct.mdp.model.Identity;
import com.alct.mdpsdksample.R;
import com.alct.mdpsdksample.demo.MockIdentity;
import com.alct.mdpsdksample.util.FormatStringUtil;
import com.alct.mdpsdksample.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {
    private Context context;

    //region control
    private TextView warningView;
    private EditText driverIdentityView;
    private EditText phoneNumberView;
    private EditText firstEnterpriseCodeView;
    private EditText firstLoginKeyView;
    private EditText firstLoginSecretView;
    private EditText secondEnterpriseCodeView;
    private EditText secondLoginKeyView;
    private EditText secondLoginSecretView;
    private EditText thirdEnterpriseCodeView;
    private EditText thirdLoginKeyView;
    private EditText thirdLoginSecretView;
    private Button clearButton;
    private Button mockButton;
    private Button mockClearButton;
    private Button registerButton;
    private Button multiRegisterButton;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initView() {
        context = RegisterActivity.this;
        warningView = (TextView) findViewById(R.id.warning_message);

        driverIdentityView = (EditText) findViewById(R.id.driverIdentity);
        firstEnterpriseCodeView = (EditText) findViewById(R.id.firstEnterpriseCode);
        firstLoginKeyView = (EditText) findViewById(R.id.firstLoginKey);
        firstLoginSecretView = (EditText) findViewById(R.id.firstLoginSecret);
        secondEnterpriseCodeView = (EditText) findViewById(R.id.secondEnterpriseCode);
        secondLoginKeyView = (EditText) findViewById(R.id.secondLoginKey);
        secondLoginSecretView = (EditText) findViewById(R.id.secondLoginSecret);
        thirdEnterpriseCodeView = (EditText) findViewById(R.id.thirdEnterpriseCode);
        thirdLoginKeyView = (EditText) findViewById(R.id.thirdLoginKey);
        thirdLoginSecretView = (EditText) findViewById(R.id.thirdLoginSecret);
        phoneNumberView = (EditText) findViewById(R.id.phone_number);

        clearButton = (Button) findViewById(R.id.clear_button);
        mockButton = (Button) findViewById(R.id.mock_button);
        mockClearButton = (Button) findViewById(R.id.clear_mock_button);
        registerButton = (Button) findViewById(R.id.register_button);
        multiRegisterButton = (Button) findViewById(R.id.multi_register_button);
    }

    private void initListener() {

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningView.setText("");
            }
        });

        mockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverIdentityView.setText("370832197808070000");
                firstEnterpriseCodeView.setText("E0000095");
                firstLoginKeyView.setText("1234");
                firstLoginSecretView.setText("1234");
                secondEnterpriseCodeView.setText("E0018901");
                secondLoginKeyView.setText("12345");
                secondLoginSecretView.setText("12345");
                phoneNumberView.setText("18603811161");
            }
        });

        mockClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverIdentityView.setText("");
                firstEnterpriseCodeView.setText("");
                firstLoginKeyView.setText("");
                firstLoginSecretView.setText("");
                phoneNumberView.setText("");
            }
        });

        //region register
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstEnterpriseCodeView.getText())) {
                    warningView.setText("enterpriseCode不能为空，请输入enterpriseCode! ");
                    return;
                }

                if (TextUtils.isEmpty(firstLoginKeyView.getText())) {
                    warningView.setText("LoginKey不能为空，请输入LoginKey! ");
                    return;
                }

                if (TextUtils.isEmpty(firstLoginSecretView.getText())) {
                    warningView.setText("LoginSecret不能为空，请输入LoginSecret! ");
                    return;
                }

                if (TextUtils.isEmpty(driverIdentityView.getText())) {
                    warningView.setText("身份证号码不能为空，请输入身份证号! ");
                    return;
                }

                String enterpriseCode = firstEnterpriseCodeView.getText().toString();
                String appIdentity = firstLoginKeyView.getText().toString();
                String appKey = firstLoginSecretView.getText().toString();

                String driverIdentity = driverIdentityView.getText().toString();
                warningView.setText("正在注册...");

                Identity identity = new Identity();
                identity.setAppIdentity(appIdentity);
                identity.setAppKey(appKey);
                identity.setEnterpriseCode(enterpriseCode);
                identity.setDriverIdentity(driverIdentity);

                MDPLocationCollectionManager.register(RegisterActivity.this,
                        identity,
                        getOnResultListener());
            }
        });
        //endregion

        multiRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(firstEnterpriseCodeView.getText())) {
                    warningView.setText("enterpriseCode不能为空，请输入enterpriseCode! ");
                    return;
                }

                if (TextUtils.isEmpty(firstLoginKeyView.getText())) {
                    warningView.setText("LoginKey不能为空，请输入LoginKey! ");
                    return;
                }

                if (TextUtils.isEmpty(firstLoginSecretView.getText())) {
                    warningView.setText("LoginSecret不能为空，请输入LoginSecret! ");
                    return;
                }

                if (TextUtils.isEmpty(driverIdentityView.getText())) {
                    warningView.setText("身份证号码不能为空，请输入身份证号! ");
                    return;
                }

                String enterpriseCode = firstEnterpriseCodeView.getText().toString();
                String appIdentity = firstLoginKeyView.getText().toString();
                String appKey = firstLoginSecretView.getText().toString();

                String secondEnterpriseCode = secondEnterpriseCodeView.getText().toString();
                String secondAppIdentity = secondLoginKeyView.getText().toString();
                String secondAppKey = secondLoginSecretView.getText().toString();

                String thirdEnterpriseCode = thirdEnterpriseCodeView.getText().toString();
                String thirdAppIdentity = thirdLoginKeyView.getText().toString();
                String thirdAppKey = thirdLoginSecretView.getText().toString();

                String driverIdentity = driverIdentityView.getText().toString();
                warningView.setText("正在注册...");

                List<EnterpriseIdentity> enterprises = new ArrayList<>();

                EnterpriseIdentity firstEnterprise = new EnterpriseIdentity();
                firstEnterprise.setAppIdentity(appIdentity);
                firstEnterprise.setAppKey(appKey);
                firstEnterprise.setEnterpriseCode(enterpriseCode);
                enterprises.add(firstEnterprise);

                if (!FormatStringUtil.isNullOrEmpty(secondEnterpriseCode)
                        && !FormatStringUtil.isNullOrEmpty(secondAppIdentity)
                        && !FormatStringUtil.isNullOrEmpty(secondAppKey)) {
                    EnterpriseIdentity secondEnterprise = new EnterpriseIdentity();
                    secondEnterprise.setAppIdentity(secondAppIdentity);
                    secondEnterprise.setAppKey(secondAppKey);
                    secondEnterprise.setEnterpriseCode(secondEnterpriseCode);
                    enterprises.add(secondEnterprise);
                }

                if (!FormatStringUtil.isNullOrEmpty(thirdEnterpriseCode)
                        && !FormatStringUtil.isNullOrEmpty(thirdAppIdentity)
                        && !FormatStringUtil.isNullOrEmpty(thirdAppKey)) {
                    EnterpriseIdentity thirdEnterprise = new EnterpriseIdentity();
                    thirdEnterprise.setAppIdentity(thirdAppIdentity);
                    thirdEnterprise.setAppKey(thirdAppKey);
                    thirdEnterprise.setEnterpriseCode(thirdEnterpriseCode);
                    enterprises.add(thirdEnterprise);
                }

                MDPLocationCollectionManager.register(RegisterActivity.this,
                        MockIdentity.initMultiEnterprise(enterprises, driverIdentity),
                        getOnResultListener());
            }
        });
    }

    private OnResultListener getOnResultListener() {
        return new OnResultListener() {
            @Override
            public void onSuccess() {
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        startActivity(new Intent(context, LocationActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                final String tip = "失败 \nerror code:" + s + "\nerror message:" + s1;

                ViewUtil.showOnDialogUi(context, tip);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(tip);
                    }
                });
            }
        };
    }
}