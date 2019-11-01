package com.innovidio.androidbootstrap.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovidio.androidbootstrap.R;

public abstract class CustomDeleteDialog{


    private Dialog dialog;
    private TextView title, message;
    private Button yesButton, noButton;
    private ImageView image;
    private Context context;

    private String dialogTitleText, dialogBodyText, dialogBtnNegativeText, dialogBtnPositiveText;
    private int imageView;


    public CustomDeleteDialog(Context context, String titleText, String bodyText, String btnNegativeText, String btnPositiveText, int imageResource) {
        this.context = context;
        this.dialogTitleText = titleText;
        this.dialogBodyText = bodyText;
        this.dialogBtnNegativeText = btnNegativeText;
        this.dialogBtnPositiveText = btnPositiveText;
        this.imageView = imageResource;
    }


    public void createDialog() {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.dialog_custom, null, false);

        if (Build.VERSION.SDK_INT >= 21) {
            dialog = new Dialog(context);
        } else {
            dialog = new Dialog(context);
        }

        try {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        dialog.setContentView(view);

        title = view.findViewById(R.id.dialog_title);
        message = view.findViewById(R.id.dialog_description);
        yesButton = view.findViewById(R.id.btn_yes);
        noButton = view.findViewById(R.id.btn_no);
        image = view.findViewById(R.id.dialog_image_icon);

        title.setText(dialogTitleText);
        message.setText(dialogBodyText);
        noButton.setText(dialogBtnNegativeText);
        yesButton.setText(dialogBtnPositiveText);
        image.setImageResource(imageView);

        noButton.setOnClickListener(this::onClick);
        yesButton.setOnClickListener(this::onClick);

    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void showDialog() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void showNegativeBtn(boolean isVisible) {
        noButton.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }

    public void showPositive(boolean isVisible) {
        yesButton.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }

    public void hideDialog() {
        dialog.hide();
        dialog.cancel();
    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_no:
                onNegativeBtnClick(dialog);
                break;

            case R.id.btn_yes:
                onPositiveBtnClick(dialog);
                break;
        }
    }


    public abstract void onNegativeBtnClick(Dialog dialog);

    public abstract void onPositiveBtnClick(Dialog dialog);

}
