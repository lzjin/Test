package com.danqiu.myapplication.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danqiu.myapplication.R;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excele);
        ButterKnife.bind(this);
        requestPermission();

        excelPath = getExcelDir()+ "/Exceldemo.xls";

        excelFile = new File(excelPath);
        createExcel(excelFile);


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
                Label lbl5 = new Label(4, 0, "图片1");
                Label lbl6 = new Label(5, 0, "图片2");

                ws.addCell(lbl1);
                ws.addCell(lbl2);
                ws.addCell(lbl3);
                ws.addCell(lbl4);
                ws.addCell(lbl5);
                ws.addCell(lbl6);

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

        try {
            Workbook oldWwb = Workbook.getWorkbook(excelFile);
            wwb = Workbook.createWorkbook(excelFile, oldWwb);
            WritableSheet ws = wwb.getSheet(0);

            // 当前行数
            int row = ws.getRows();


            ws.addCell(new Label(0, row, args[0] + ""));
            ws.addCell(new Label(1, row, args[1] + ""));
            ws.addCell(new Label(2, row, args[2] + ""));
            ws.addCell(new Label(3, row, args[3] + ""));
            File f1= new File("/storage/emulated/0/AAASewages/images/a_0_1558504666350.png");
            File f2=new File("/storage/emulated/0/AAASewages/images/a_1_1558504666352.png");

            ws.addImage(new WritableImage(4, row, 1, 1,f1));

            ws.addImage(new WritableImage(5, row, 1, 1,f2 ));

            // 从内存中写入文件中,只能刷一次.
            wwb.write();
            wwb.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void excelImageTest(WritableSheet ws) {
//
//            FileOutputStream fileOut = null;
//            BufferedImage bufferImg = null;
//            //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
//            try {
//                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//                bufferImg = ImageIO.read(new File("F:/图片/照片/无名氏/小昭11.jpg"));
//                ImageIO.write(bufferImg, "jpg", byteArrayOut);
//
//                HSSFWorkbook wb = new HSSFWorkbook();
//                HSSFSheet sheet1 = wb.createSheet("test picture");
//                //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//                HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
//                //anchor主要用于设置图片的属性
//                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 1, 1, (short) 5, 8);
//                anchor.setAnchorType(3);
//                //插入图片
//                patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
//                fileOut = new FileOutputStream("D:/测试Excel.xls");
//                // 写入excel文件
//                wb.write(fileOut);
//                System.out.println("----Excle文件已生成------");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }finally{
//                if(fileOut != null){
//                    try {
//                        fileOut.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//    }

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
}
