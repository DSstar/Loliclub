package com.loliclub.bluetoothtools.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loliclub.bluetoothtools.R;

public class MyCustomDialog extends Dialog {


    protected MyCustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyCustomDialog(Context context) {
        super(context);
    }

    public MyCustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mContent;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private View mContentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        // id R.
        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public Builder setContent(int rId) {
            if (rId == 0)
                return this;
            this.mContent = mContext.getString(rId);
            return this;
        }

        public Builder setTitle(int rId) {
            if (rId == 0)
                return this;
            this.mTitle = mContext.getString(rId);
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.mContentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            if (positiveButtonText == 0)
                return this;
            this.mPositiveButtonText = mContext.getString(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            if (negativeButtonText == 0)
                return this;
            this.mNegativeButtonText = mContext.getString(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.mNegativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public MyCustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyCustomDialog dialog = new MyCustomDialog(mContext,
                    R.style.MyCustomDialog);
            View layout = inflater.inflate(R.layout.dialog_custom, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.dialog_title)).setText(mTitle);
            } else {
                ((TextView) layout.findViewById(R.id.dialog_title))
                        .setVisibility(View.GONE);
            }
            // set the confirm button
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_btn_positive))
                        .setText(mPositiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_btn_positive))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_btn_positive).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.dialog_btn_negative))
                        .setText(mNegativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.dialog_btn_negative))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.dialog_btn_negative).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (mContent != null) {
                ((TextView) layout.findViewById(R.id.dialog_content))
                        .setText(mContent);
            } else if (mContentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .addView(mContentView, new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
