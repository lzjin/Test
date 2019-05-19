package com.danqiu.myapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.LogUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pub.devrel.easypermissions.EasyPermissions;

public class ExceleActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.save)
    Button save;

    private String name;
    private String sex;
    private String phone;
    private String address;

    private String excelPath;


    private WritableWorkbook wwb;
    private File excelFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excele);
        ButterKnife.bind(this);
        requestPermission();

        excelPath = getExcelDir()+ "/Exceldemo.xls";

        excelFile = new File(excelPath);
        createExcel(excelFile);
    }

    private void requestPermission(){
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "为了更好的用户体验需要获取以下权限", 1, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode){
            case 1:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    // 创建excel表.
    public void createExcel(File file) {
        WritableSheet ws = null;
        try {
            if (!file.exists()) {
                wwb = Workbook.createWorkbook(file);

                ws = wwb.createSheet("sheet1", 0);

                // 在指定单元格插入数据
                Label lbl1 = new Label(0, 0, "姓名");
                Label lbl2 = new Label(1, 0, "性别");
                Label lbl3 = new Label(2, 0, "电话");
                Label lbl4 = new Label(3, 0, "地址");

                ws.addCell(lbl1);
                ws.addCell(lbl2);
                ws.addCell(lbl3);
                ws.addCell(lbl4);

                // 从内存中写入文件中
                wwb.write();
                wwb.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将数据存入到Excel表中
    public void writeToExcel(Object... args) {

        if(excelFile==null){
            LogUtil.i("test-------------空");
        }

        try {
            Workbook oldWwb = Workbook.getWorkbook(excelFile);
            wwb = Workbook.createWorkbook(excelFile, oldWwb);
            WritableSheet ws = wwb.getSheet(0);
            // 当前行数
            int row = ws.getRows();
            Label lab1 = new Label(0, row, args[0] + "");
            Label lab2 = new Label(1, row, args[1] + "");
            Label lab3 = new Label(2, row, args[2] + "");
            Label lab4 = new Label(3, row, args[3] + "");
            ws.addCell(lab1);
            ws.addCell(lab2);
            ws.addCell(lab3);
            ws.addCell(lab4);

            // 从内存中写入文件中,只能刷一次.
            wwb.write();
            wwb.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.save)
    public void onViewClicked() {
        name = etName.getText().toString().trim();
        sex = etSex.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        address = etAddress.getText().toString().trim();

        //权限
        writeToExcel(name,sex,phone,address);

    }



    // 获取Excel文件夹
    public static String getExcelDir() {
        // SD卡指定文件夹
        File dir = new File(Environment.getExternalStorageDirectory() + "/Excel" + "/person");

        if (dir.exists()) {
            Log.i("test", "保存路径存在,"+dir.toString());
            return dir.toString();
        } else {
            dir.mkdirs();
            Log.i("test", "保存路径不存在,"+dir.toString());
            return dir.toString();
        }
    }
}
