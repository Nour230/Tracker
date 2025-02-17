package com.example.testtracker.view.forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.testtracker.R;
import com.google.android.material.button.MaterialButton;

public class Dialog {
    public static void showAlertDialog(Context context, String message) {
        // Inflate the custom dialog layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.popup, null);

        // Initialize the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Bind views from the custom layout
        TextView messageTextView = dialogView.findViewById(R.id.tv_alert_message);
        MaterialButton dismissButton = dialogView.findViewById(R.id.btn_dismiss);

        // Set the dialog message
        messageTextView.setText(message);

        // Handle the dismiss button click
        dismissButton.setOnClickListener(v -> dialog.dismiss());
    }

}
