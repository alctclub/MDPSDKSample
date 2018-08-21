package com.alct.mdpsdksample.ui;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alct.mdp.MDPLocationCollectionManager;
import com.alct.mdp.callback.OnDownloadResultListener;
import com.alct.mdp.callback.OnResultListener;
import com.alct.mdp.model.Invoice;
import com.alct.mdp.model.ShipmentStatusEnum;
import com.alct.mdp.response.GetInvoicesResponse;
import com.alct.mdpsdksample.R;
import com.alct.mdpsdksample.demo.MockGoods;
import com.alct.mdpsdksample.demo.MockImage;
import com.alct.mdpsdksample.demo.MockLocation;
import com.alct.mdpsdksample.util.FormatStringUtil;
import com.alct.mdpsdksample.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends BaseActivity {
    public static final String TIP_STRING = "运单：%s  \n操作：%s  结果：";
    private Context context;

    //region control
    private TextView warningView;
    private EditText shipmentCodeView;
    private EditText currentEnterpriseCodeView;
    private EditText nfcView;
    private EditText driverInvoiceCode;
    private EditText pageSize;
    private EditText currentPage;
    private Button buttonClear;
    private Button buttonViewShipmentStatus;

    private Button buttonPickup;
    private Button buttonUnload;
    private Button buttonSign;
    private Button buttonPod;
    private Button buttonUploadUnloadImage;
    private Button buttonUploadPODImage;
    private Button buttonDeleteUnloadImage;
    private Button buttonDeletePODImage;
    private Button buttonGetUnloadImages;
    private Button buttonGetPODImages;
    private Button buttonGetUnloadImage;
    private Button buttonGetPODImage;
    private Button buttonCheckNfc;
    private Button buttonGetInvoices;
    private Button buttonConfirmInvoice;
    private Button mockButton;
    private Button mockClearButton;
    //endregion

    private OnDownloadResultListener imageNameListener;
    private OnDownloadResultListener imageListener;
    private OnDownloadResultListener getInvoicesListener;
    private OnResultListener confirmInvoiceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initEvent();
        initListener();
        onNewIntent(getIntent());
    }

    private void initView() {
        context = LocationActivity.this;
        warningView = (TextView) findViewById(R.id.warning_message);
        shipmentCodeView = (EditText) findViewById(R.id.shipmentCode);
        currentEnterpriseCodeView = (EditText) findViewById(R.id.currentEnterprise);
        nfcView = (EditText) findViewById(R.id.nfcId);
        driverInvoiceCode = (EditText) findViewById(R.id.driverInvoiceCode);
        pageSize = (EditText) findViewById(R.id.pageSize);
        currentPage = (EditText) findViewById(R.id.currentPage);
        mockButton = (Button) findViewById(R.id.mock_button);
        mockClearButton = (Button) findViewById(R.id.clear_mock_button);
        buttonClear = (Button) findViewById(R.id.clear_button);
        buttonViewShipmentStatus = (Button) findViewById(R.id.view_shipment_status_button);
        buttonPickup = (Button) findViewById(R.id.buttonPickup);
        buttonUnload = (Button) findViewById(R.id.buttonUnload);
        buttonSign = (Button) findViewById(R.id.buttonSign);
        buttonPod = (Button) findViewById(R.id.buttonPod);
        buttonUploadUnloadImage = (Button) findViewById(R.id.buttonUploadUnloadImage);
        buttonUploadPODImage = (Button) findViewById(R.id.buttonUploadPODImage);
        buttonDeleteUnloadImage = (Button) findViewById(R.id.buttonDeleteUnloadImage);
        buttonDeletePODImage = (Button) findViewById(R.id.buttonDeletePODImage);
        buttonGetUnloadImages = (Button) findViewById(R.id.buttonGetUnloadImages);
        buttonGetPODImages = (Button) findViewById(R.id.buttonGetPODImages);
        buttonGetUnloadImage = (Button) findViewById(R.id.buttonGetUnloadImage);
        buttonGetPODImage = (Button) findViewById(R.id.buttonGetPODImage);
        buttonCheckNfc = (Button) findViewById(R.id.buttonCheckNfc);
        buttonGetInvoices = (Button) findViewById(R.id.buttonGetInvoices);
        buttonConfirmInvoice = (Button) findViewById(R.id.buttonConfirmInvoice);
    }

    private void initListener() {
        if (!super.hasNfcAdapter()) {
            nfcView.setText("不支持nfc");
        }

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningView.setText("");
            }
        });

        mockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentCodeView.setText("CSP20180504003");
                currentEnterpriseCodeView.setText("E0000095");
                nfcView.setText("123456");
                pageSize.setText("1");
                currentPage.setText("1");
                }
        });

        mockClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentCodeView.setText("");
                nfcView.setText("");
                }
        });

        //region shipment operate
        buttonViewShipmentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.getShipmentStatus(context, shipmentCode, currentEnterpriseCode
                        , new OnDownloadResultListener() {
                            @Override
                            public void onSuccess(Object o) {
                                ShipmentStatusEnum result = (ShipmentStatusEnum) o;
                                final String status = result.name();
                                ViewUtil.showOnDialogUi(context, status);
                                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                                    @Override
                                    public void work() {
                                        warningView.setText(status);
                                    }});
                            }

                            @Override
                            public void onFailure(String s, String s1) {
                                final String tip = s + s1;
                                ViewUtil.showOnDialogUi(context, s + s1);
                                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                                    @Override
                                    public void work() {
                                        warningView.setText(tip);
                                    }
                                });
                            }
                        });
            }
        });

        buttonPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.pickup(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockLocation.init(),
                        getOnResultListener(shipmentCode, "Pickup"));
            }
        });

        buttonUnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.unload(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockLocation.init(),
                        getOnResultListener(shipmentCode, "Unload"));
            }
        });
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.sign(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockLocation.init(), MockGoods.init(),
                        getOnResultListener(shipmentCode, "Sign"));
            }
        });
        buttonPod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.pod(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockLocation.init(),
                        getOnResultListener(shipmentCode, "Pod"));
            }
        });

//endregion

        //region image
        buttonUploadUnloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.uploadUnloadImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockImage.init("unloadImage"),
                        getOnResultListener(shipmentCode, "UploadUnloadImage"));
            }
        });

        buttonUploadPODImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.uploadPODImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, MockImage.init("podImage"),
                        getOnResultListener(shipmentCode, "UploadPODImage"));
            }
        });

        buttonDeleteUnloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.deleteUnloadImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, "unloadImage", "jpeg",
                        getOnResultListener(shipmentCode, "DeleteUnloadImage"));
            }
        });

        buttonGetPODImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");


                MDPLocationCollectionManager.getPODImageNames(LocationActivity.this, shipmentCode, currentEnterpriseCode, imageNameListener);
            }
        });
        buttonGetUnloadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");


                MDPLocationCollectionManager.getUnloadImageNames(LocationActivity.this, shipmentCode, currentEnterpriseCode, imageNameListener);
            }
        });
        buttonGetPODImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");


                MDPLocationCollectionManager.downloadPODImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, "podImage", "jpeg", imageListener);
            }
        });
        buttonGetUnloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");


                MDPLocationCollectionManager.downloadUnloadImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, "unloadImage", "jpeg", imageListener);
            }
        });
        buttonDeletePODImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                warningView.setText("正在操作...");


                MDPLocationCollectionManager.deletePODImage(LocationActivity.this, shipmentCode, currentEnterpriseCode, "podImage", "jpeg",
                        getOnResultListener(shipmentCode, "DeletePODImage"));
            }
        });
//endregion

        //region nfc
        buttonCheckNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shipmentCode = checkShipmentCode();
                String currentEnterpriseCode = checkEnterpriseCode();

                if (shipmentCode == null) {
                    return;
                }
                String nfcId = nfcView.getText().toString();

                if (TextUtils.isEmpty(nfcId)) {
                    warningView.setText("nfcId不能为空，请输入nfcId! ");
                 }
                warningView.setText("正在操作...");

                MDPLocationCollectionManager.checkNfc(LocationActivity.this, shipmentCode, currentEnterpriseCode, nfcId,
                        getOnResultListener(shipmentCode, "CheckNfc"));
            }
        });
        //endregion

        //region Invoice
        buttonGetInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentEnterpriseCode = checkEnterpriseCode();

                if (TextUtils.isEmpty(pageSize.getText()) || TextUtils.isEmpty(currentPage.getText())) {
                    warningView.setText("pageSize和currentPage都不能为空，请输入pageSize和currentPage! ");
                    return;
                }

                warningView.setText("正在操作...");
                MDPLocationCollectionManager.getInvoices(context, currentEnterpriseCode, Integer.parseInt(pageSize.getText().toString()), Integer.parseInt(currentPage.getText().toString()), getInvoicesListener);
            }
        });

        buttonConfirmInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentEnterpriseCode = checkEnterpriseCode();

                if (TextUtils.isEmpty(driverInvoiceCode.getText())) {
                    warningView.setText("driverInvoiceCode不能为空，请输入driverInvoiceCode! ");
                    return;
                }

                warningView.setText("正在操作...");


                MDPLocationCollectionManager.confirmInvoice(context, currentEnterpriseCode, driverInvoiceCode.getText().toString().trim(), confirmInvoiceListener);
            }
        });
        //endregion
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            processIntent(intent);
        }
    }

    private void processIntent(Intent intent) {
        byte[] myNFCId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        String nfcId = FormatStringUtil.getHexString(myNFCId);

        nfcView.setText(nfcId);
    }

    @Nullable
    private String checkShipmentCode() {
        String shipmentCode = shipmentCodeView.getText().toString();
        if (TextUtils.isEmpty(shipmentCode)) {
            warningView.setText("运单号不能为空，请输入运单号! ");
            return null;
        }
        return shipmentCode;
    }

    @Nullable
    private String checkEnterpriseCode() {
        String enterpriseCode = currentEnterpriseCodeView.getText().toString();
        if (TextUtils.isEmpty(enterpriseCode)) {
            warningView.setText("企业号不能为空，请输入企业号! ");
            return null;
        }
        return enterpriseCode;
    }

    private void initEvent() {
        imageListener = new OnDownloadResultListener() {
            @Override
            public void onSuccess(Object o) {
                final String imagePath = (String) o;
                ViewUtil.showOnDialogUi(context, imagePath);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(imagePath);
                }});
            }

        @Override
            public void onFailure(String s, String s1) {
                final String tip = s + s1;
                ViewUtil.showOnDialogUi(context, s + s1);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(tip);
                    }
                });
            }
        };

        imageNameListener = new OnDownloadResultListener() {
            @Override
            public void onSuccess(Object o) {
                ArrayList<String> nameList = null;

                if (o instanceof ArrayList) {
                    nameList = (ArrayList<String>) o;
                }

                if (nameList == null) {
                    warningView.setText("内部错误");
                    return;
            }

                StringBuilder names = new StringBuilder();

                for (String name : nameList) {
                    names.append(name);
                    names.append(";");
        }

                final String namesToShow = names.toString();
                ViewUtil.showOnDialogUi(context, namesToShow);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(namesToShow);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                final String tip = s + s1;
                ViewUtil.showOnDialogUi(context, s + s1);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(tip);
                    }
                });
            }
    };

        getInvoicesListener = new OnDownloadResultListener() {
            String result = "";

    @Override
            public void onSuccess(Object o) {
                GetInvoicesResponse response = null;

                if (o instanceof GetInvoicesResponse) {
                    response = (GetInvoicesResponse) o;
    }

                if (response == null) {
                    warningView.setText("内部错误");
                    return;
    }
                result = "";

                result +=
                        "PageSize:" + response.getPageSize() + "\n" +
                        "CurrentPage:" + response.getCurrentPage() + "\n" +
                        "SortField:" + response.getSortField() + "\n" +
                        "SortDirection:" + response.getSortDirection() + "\n" +
                        "TotalCount:" + response.getTotalCount() + "\n" +
                        "TotalPage:" + response.getTotalPage() + "\n" +
                        "--------------------\n";

                List<Invoice> invoices = response.getDriverInvoices();

                if (null == invoices) {
                    warningView.setText("发票列表为空");
                    return;
                }

                for (Invoice invoice : invoices) {
                    if (null != invoice) {
                        result +=
                        "InvoiceReceiverName:" + invoice.getInvoiceReceiverName() + "\n" +
                        "TaxRate:" + invoice.getTaxRate() + "\n" +
                        "TaxAmount:" + invoice.getTaxAmount() + "\n" +
                        "TotalAmount:" + invoice.getTotalAmount() + "\n" +
                        "TotalAmountIncludeTax:" + invoice.getTotalAmountIncludeTax() + "\n" +
                        "DriverInvoiceCode:" + invoice.getDriverInvoiceCode() + "\n" +
                        "--------------------\n";
                    }
                }

                ViewUtil.showOnDialogUi(context, result);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(result);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                final String tip = "getInvoices失败 \nerror code:" + s + "\nerror message:" + s1;

                ViewUtil.showOnDialogUi(context, tip);
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(tip);
                    }
                });
            }
        };

        confirmInvoiceListener = new OnResultListener() {
            @Override
            public void onSuccess() {
                ViewUtil.showOnDialogUi(context, "confirmInvoice success");
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText("confirmInvoice success");
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                final String tip = "confirmInvoice 失败 \nerror code:" + s + "\nerror message:" + s1;

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

    private OnResultListener getOnResultListener(String shipmentCode, String operate) {
        final String tipTitle = String.format(Locale.getDefault(), TIP_STRING, shipmentCode, operate);
        return new OnResultListener() {
            @Override
            public void onSuccess() {
                ViewUtil.showOnDialogUi(context, tipTitle + "success");
                ViewUtil.workOnUi(context, new ViewUtil.TaskOnUi() {
                    @Override
                    public void work() {
                        warningView.setText(tipTitle + "success");
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                final String tip = tipTitle + "失败 \nerror code:" + s + "\nerror message:" + s1;

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