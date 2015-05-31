package com.example.administrador.myapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.models.entities.ServiceOrder;
import com.example.administrador.myapplication.models.persistence.DatabaseContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReportsActivity extends ActionBarActivity {

    private EditText editEmail;
    private RadioGroup radioReportsGroup;
    private Button btnSubmit;
    private RadioButton selectedRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        editEmail = (EditText) findViewById(R.id.editEmail);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        radioReportsGroup = (RadioGroup) findViewById(R.id.radioReports);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioReportsGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedId);
                switch (selectedId) {
                    case R.id.radioBtnReportAll:
                        if (isValidEmail()) {
                            generateReportAll();
                        } break;
                    case R.id.radioBtnReportActivePaid:
                        if (isValidEmail()) {
                            generateReportActivePaid();
                        } break;
                }
            }
        });
    }

    public void generateReportAll() {
        StringBuilder report = new StringBuilder();
        report.append("REPORT OF SERVICE ORDERS").append("\n\n");
        final List<ServiceOrder> serviceOrders = ServiceOrder.getAll();
        for (ServiceOrder serviceOrder : serviceOrders) {
            report.append(formatForEmail(serviceOrder)).append("\n\n");
        }
        submitEmail(getString(R.string.lbl_reportall), report.toString());
    }

    public void generateReportActivePaid() {
        StringBuilder report = new StringBuilder();
        report.append("REPORT OF SERVICE ORDERS").append("\n\n");
        final List<ServiceOrder> serviceOrders = ServiceOrder.getAllByStatusAndPayment(true, true);
        Double amount = new Double(0);
        for (ServiceOrder serviceOrder : serviceOrders) {
            amount += serviceOrder.getValue();
            report.append(formatForEmail(serviceOrder)).append("\n\n");
        }
        report.append("TOTAL: U$"+ amount.toString() + "\n\n");
        submitEmail(getString(R.string.lbl_reportall), report.toString());
    }

    private void submitEmail(final String subject, final String message) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        final String developerEmail = editEmail.getText().toString().trim();
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{developerEmail});

        StringBuilder subjectBuilder = new StringBuilder();
        subjectBuilder.append("[ ").append(getResources().getString(R.string.app_name));
        subjectBuilder.append(" - ").append(subject).append(" ]");
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectBuilder.toString());

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final Calendar cal = Calendar.getInstance();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(message).append("\n\n")
                .append("Date: ").append(dateFormat.format(cal.getTime()));
        intent.putExtra(Intent.EXTRA_TEXT, messageBuilder.toString());

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.msg_send_email)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReportsActivity.this, getString(R.string.msg_error_send_email), Toast.LENGTH_LONG).show();
        }
    }

    private String formatForEmail(ServiceOrder serviceOrder) {
        StringBuilder formatted = new StringBuilder();
        formatted.append(getString(R.string.lbl_service_order_id) + "#").append(serviceOrder.getId() + "\n");
        formatted.append(DatabaseContract.DESCRIPTION + ": ").append(serviceOrder.getDescription() + "\n");
        formatted.append(DatabaseContract.VALUE + ": ").append(serviceOrder.getValue() + "\n");
        formatted.append(DatabaseContract.PAID + ": ")
                .append(serviceOrder.isPaid() ? getString(R.string.lbl_yes) : getString(R.string.lbl_no)).append("\n");
        formatted.append(DatabaseContract.ACTIVE + ": ")
                .append(serviceOrder.isPaid() ? getString(R.string.lbl_yes) : getString(R.string.lbl_no)).append("\n");

        return formatted.toString();
    }

    public boolean isValidEmail() {
        boolean isValid = true;
        editEmail.setError(null);
        if (TextUtils.isEmpty(editEmail.getText())) {
            editEmail.setError(getString(R.string.msg_mandatory));
            if (isValid) {
                isValid = false;
            }
        }
        return isValid;
    }
}