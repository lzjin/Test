package com.danqiu.myapplication.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.danqiu.myapplication.bean.EvaluationBean;
import com.danqiu.myapplication.bean.MsgBean;
import com.danqiu.myapplication.bean.TestBean;
import com.danqiu.myapplication.config.Constants;
import com.danqiu.myapplication.retrofit.JsDownloadListener;
import com.danqiu.myapplication.retrofit.ProgressSubscriber;
import com.danqiu.myapplication.utils.File_Utils;
import com.danqiu.myapplication.utils.GsonUtil;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.Public_utils;
import com.danqiu.myapplication.utils.RetDownload_Utils;
import com.danqiu.myapplication.views.ProgressDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/9/27.
 *
 */
@SuppressWarnings("unchecked")
public class LoginModel extends BaseModel{
    public Context mContext;
    public ProgressDialog dialog;
    public ProgressDialogFragment dialogFragment;

    public LoginModel(Context mContext,ProgressDialog dialog) {
        this.mContext = mContext;
        this.dialog=dialog;
    }

    /**
     * 登录
     * @param map
     * 配置解析，返回数据已经解析
     */
    public void login(Map<String, String> map){
        apiService.login(map).compose(schedulersTransformer())
                  .subscribe(new ProgressSubscriber(mContext,dialog) {
                   @Override
                   protected void _onNext(Object o) {
                       TestBean bean = (TestBean) o;
                       MLog.i("test", "MovieModelImpl-------->>onSuccess="+bean.getMessage());


                   }
                   @Override
                   protected void _onError(String message) {

                   }
        });
    }

    /**
     * 登录
     * @param map
     * 返回的数据需要解析
     */
    public void login_body(Map<String, String> map){
        apiService.login(map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext,dialog) {
                    @Override
                    protected void _onNext(Object o) {
                       ResponseBody responseBody = (ResponseBody) o;
                       JSONObject jsonObject = null;
                       try {
                           jsonObject = new JSONObject(responseBody.string());
                           //MLog.iJsonFormat(jsonObject.toString());
                           MsgBean bean= GsonUtil.getUser(jsonObject.toString(), MsgBean.class);
                           MLog.i("test", "MovieModelImpl-------->>onSuccess="+bean.getMessage());

                       } catch (JSONException e) {
                           e.printStackTrace();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                    }
                    @Override
                    protected void _onError(String message) {

                    }
                });
    }

    /**
     * 评价
     * @param map
     */
    public void getEvaluationList(String token, Map<String, String> map){
        apiService.getEvaluationList(token,map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext,dialog) {
                    @Override
                    protected void _onNext(Object o) {
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            // MLog.iJsonFormat(jsonObject.toString());
                            EvaluationBean bean= GsonUtil.getUser(jsonObject.toString(), EvaluationBean.class);
                             MLog.i("test", "MovieModelImpl-------->>onSuccess="+bean.getMessage());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    protected void _onError(String message) {

                    }
                });
    }
    /**
     * 评价
     * @param
     */
    public void getEvaluationList(String token,int pageNo,int pageSize){
        apiService.getEvaluationLists(token,pageNo,pageSize).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext,dialog) {
                    @Override
                    protected void _onNext(Object o) {
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            // MLog.iJsonFormat(jsonObject.toString());
                            EvaluationBean bean= GsonUtil.getUser(jsonObject.toString(), EvaluationBean.class);
                            MLog.i("test", "MovieModelImpl-------->>onSuccess="+bean.getMessage());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    protected void _onError(String message) {

                    }
                });
    }

    /**
     *
     * @param token
     * @param imagePath
     * 参考 https://blog.csdn.net/weixin_41307234/article/details/78948907
     *
     *
     */
    public void upLoadImg(String token,String imagePath){
//        MultipartBody.Part[] part = new MultipartBody.Part[fileList.size()];
//        for (int i = 0; i < part.length; i++) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
//            part[i] = MultipartBody.Part.createFormData("attachment", fileList.get(i).getName(), requestFile);
//        }

        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody multipartBody =new MultipartBody.Builder()
                //注意，后台需要的参数名与参数值
                .addFormDataPart("file_name",file.getName(),requestFile)
                .build();

        apiService.upLoadImg(token,multipartBody).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext,dialog) {
                    @Override
                    protected void _onNext(Object o) {
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            // MLog.iJsonFormat(jsonObject.toString());
                            EvaluationBean bean= GsonUtil.getUser(jsonObject.toString(), EvaluationBean.class);
                            MLog.i("test", "MovieModelImpl-------->>onSuccess="+bean.getMessage());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    protected void _onError(String message) {

                    }
                });
    }

    /**
     * 不带进度条
     * @param url
     * @param pathDown
     * @param saveName
     */
    public void  downloadFiles(final String url,final String pathDown, final String saveName){
        apiService.downloadApps(url).compose(schedulersTransformer())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i("test", "----------------APP下载完成 ");
                        Public_utils.installApk(mContext,pathDown, Constants.APP_FILE_NAME_APK);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test", "----------------APP下载失败: " + e.toString());
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        InputStream inputStream = null;
                        inputStream = body.byteStream();
                        try {
                            File_Utils.writeFile(inputStream ,File_Utils.mkFile(pathDown,saveName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     *  下载带进度条
     * @param url
     * @param pathDown
     * @param saveName
     * @param progressBar
     */
    public void  downloadFile(final String url,final String pathDown, final String saveName,final ProgressBar progressBar){
        final JsDownloadListener listener=new JsDownloadListener() {
            @Override
            public void onStartDownload() {
                Log.i("test", "----------------APP下载开始 ");
            }

            @Override
            public void onProgress(int progress) {
                Log.i("test", "-----------------APP下载进度: " + progress);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinishDownload() {
                Log.i("test", "----------------APP下载完成 ");
                Public_utils.installApk(mContext,pathDown, Constants.APP_FILE_NAME_APK);
            }

            @Override
            public void onFail(String errorInfo) {
                Log.i("test", "----------------APP下载失败: " + errorInfo);
            }
        };

        RetDownload_Utils downloadUtils=new RetDownload_Utils(listener );
        downloadUtils.downApkFile(url,pathDown, saveName, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.toString());
            }

            @Override
            public void onNext(Object o) {
                listener.onFinishDownload();
            }
        });
    }








}
