package com.lzj.xdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2018/12/20.
 * 系统默认弹框
 *
 * new XDialog(this)
 * .init("标题","这是内容")
 * .defaultShow(new XDialog.OnDialogListener() {
 *   @Override
 *   public void onCancel() {
 *
 *   }
 *   @Override
 *   public void onSure() {
 *
 *  }
 * });
 *
 */

public class XDialog {

    private Context mContext;
    private String mTitle;
    private String mMessage;
    private String mCancel = "取消";
    private String mSure = "确定";

    public XDialog(Context mContext) {
        this.mContext = mContext;
    }


    public static interface OnDialogListener {
        void onCancel();

        void onSure();
    }

    private OnDialogListener mDialogListener;

    /**
     * 初始化
     *
     * @param title
     * @param message
     * @return
     */
    public XDialog init(String title, String message) {
        this.mTitle = title;
        this.mMessage = message;
        return this;
    }

    /**
     * 初始化
     *
     * @param title
     * @param message
     * @param cancel
     * @param sure
     * @return
     */
    public XDialog init(String title, String message, String cancel, String sure) {
        this.mTitle = title;
        this.mMessage = message;
        this.mCancel = cancel;
        this.mSure = sure;
        return this;
    }

    /**
     * 默认显示
     *
     * @param dialogListener
     * @return
     */
    public XDialog defaultShow(final OnDialogListener dialogListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setCancelable(true)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setNegativeButton(mCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogListener.onCancel();
                            }
                        })
                .setPositiveButton(mSure,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogListener.onSure();
                            }
                        })
                .show();
        return this;
    }

    /**
     * 默认显示
     *
     * @param back
     * @param dialogListener
     * @return
     */
    public XDialog defaultShow(boolean back, final OnDialogListener dialogListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setCancelable(back)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setNegativeButton(mCancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogListener.onCancel();
                            }
                        })
                .setPositiveButton(mSure,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogListener.onSure();
                            }
                        })
                .show();
        return this;
    }
}
