package com.example.ipcstudentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ipcservice.IStudentService;
import com.example.ipcservice.Student;

public class MainActivity extends AppCompatActivity {

    private IStudentService mService;
    private EditText edtID, edtName, edtAge, edtGender, edtAddress;
    private TextView txtResult;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        bindService();
        onClick();
    }

    private void bindService() {
        Intent intent = new Intent("ACTION_1");
        intent.setPackage("com.example.ipcservice");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void onClick() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null) {
                    try {
                        mService.addStudent(edtID.getText().toString(), edtName.getText().toString(), Integer.parseInt(edtAge.getText().toString()), edtGender.getText().toString(), edtAddress.getText().toString());
                        StringBuilder result = new StringBuilder();
                        for (Student student : mService.getStudents()) {
                            result.append(student.toString() + "\n");
                        }
                        result.append("\n" + mService.getStudent("1"));
                        txtResult.setText(result.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initViews() {
        edtID = findViewById(R.id.editID);
        edtName = findViewById(R.id.editName);
        edtAge = findViewById(R.id.editAge);
        edtGender = findViewById(R.id.editGender);
        edtAddress = findViewById(R.id.editAddress);
        txtResult = findViewById(R.id.textResult);
        btnSave = findViewById(R.id.buttonSave);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IStudentService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
}
