package com.wallpad.notice.view.common;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BaseDialog  extends DialogFragment {
    private static final String dialogAction = "com.wallpad.statusbar.action.DIALOG_ON";
    private static final String dialogActionPackage = "com.wallpad.statusbar";
    private static final String actionKey = "on";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent intent = new Intent();
        intent.setPackage(dialogActionPackage);
        intent.setAction(dialogAction);
        intent.putExtra(actionKey, false);
        this.getActivity().sendBroadcast(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setPackage(dialogActionPackage);
        intent.setAction(dialogAction);
        intent.putExtra(actionKey, true);
        this.getActivity().sendBroadcast(intent);
    }
}