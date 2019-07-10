package simtechnospace.tech.basketondemand.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import simtechnospace.tech.basketondemand.R;

public class CustomAlertDialogForContact extends Dialog {

    public Context mContext;
    public Button mBtnPositiveDone;

    ImageView mImageViewCloseButton;


    public CustomAlertDialogForContact(Context context)
    {
        super(context);

        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.contact_us);


        mImageViewCloseButton = findViewById(R.id.btnOrderDoneClose);
        mBtnPositiveDone = findViewById(R.id.btnDone);



        mImageViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBtnPositiveDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    }
