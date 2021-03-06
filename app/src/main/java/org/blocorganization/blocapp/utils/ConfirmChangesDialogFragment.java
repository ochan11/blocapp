package org.blocorganization.blocapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.blocorganization.blocapp.R;

public class ConfirmChangesDialogFragment extends DialogFragment {

    private ConfirmChangesListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (ConfirmChangesListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement ConfirmChangesListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.campaign_save_dialog_text)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save Changes!
                        mListener.onConfirmSave();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing, auto closes window.
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        .setTextColor(Color.parseColor("#000000"));
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                        .setTextColor(Color.parseColor("#000000"));
            }
        });
        return alertDialog;
    }

    public interface ConfirmChangesListener {
        void onConfirmSave();
    }
}
