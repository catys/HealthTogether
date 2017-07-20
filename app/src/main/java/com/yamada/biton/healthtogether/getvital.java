package com.yamada.biton.healthtogether;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.yamada.biton.healthtogether.AsyncTasksPackage.vitalinsert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jp.co.omron.healthcare.omoron_connect.wrapper.EquipmentInfo;
import jp.co.omron.healthcare.omoron_connect.wrapper.UserProfileInfo;
import jp.co.omron.healthcare.omoron_connect.wrapper.VitalData;
import jp.co.omron.healthcare.omoron_connect.wrapper.WrapperApi;

//import com.yamada.biton.healthtogether.AsyncTasksPackage.vitalinsert;


public class getvital extends AppCompatActivity {

    WrapperApi mWrapperApi = null;
    boolean getMetaData = false;
    boolean mUseTimeZone = true;
    String Equipmenttype;
    int UserID;
    String SerialID;

    private static final String APP_ID = "XCXvv8SD";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getvital);


        getMetaData = false;


        mUseTimeZone = true;



        mWrapperApi = new WrapperApi(this, APP_ID, getPackageName());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


        public void getv(View v) {
            mWrapperApi = new WrapperApi(v.getContext(), APP_ID, getPackageName());
            ArrayList<EquipmentInfo> equipmentInfoList = mWrapperApi.getDeveiceList(true);



            for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                Equipmenttype = equipmentInfo.getDeviceType();
                UserID = equipmentInfo.getUserId();
                SerialID =equipmentInfo.getSerialId();

            }

            long[] time = getFromToTime();

            String index = "";




            Log.d("Nyu", "index   = " + index);

            if (index.equals("")) {
                index = null;
            }




            String tmpDeviceNamne = null;
            int tmpoffset = 0, tmpcount = 7, tmporder = 2, tmpuserid = -1;
            String tmpserialid;


            tmpDeviceNamne = Equipmenttype;
            tmpuserid = UserID;
            tmpserialid = SerialID;

            ArrayList<VitalData> vitalDataList = mWrapperApi.GetVitalData(tmpDeviceNamne, index, time[0], time[1],
                    tmporder,
                    tmpoffset,
                    tmpcount,
                    getMetaData,
                    tmpuserid,
                    tmpserialid);




            String tvText = "";
            ArrayList<Integer> DataList = new ArrayList<Integer>();
            if (vitalDataList != null) {

                for (VitalData vital : vitalDataList) {

//入手データメモ：1,体重 2,体脂肪率 3,基礎代謝 4,骨格筋率 5,BMI 6,体年齢 7,内臓脂肪レベル
                    DataList.add(vital.getMeasurement());

                }

                UserProfileInfo info = mWrapperApi.getUserProfile();
                vitalinsert viin = new vitalinsert();
                viin.vitalpost("ここにmailaddress",DataList, info);
                Toast toast = Toast.makeText(getvital.this,"ボタン押しました",Toast.LENGTH_LONG);
                toast.show();
            } else {
                tvText = "データ取得エラー";
            }


        }


    private long[] getFromToTime() {

        int yyyy, mm, dd, hh;
        long from = 0, to = 0;
        Calendar cal = null;

        TimeZone tz;
        if (mUseTimeZone) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone("GMT");
        }
        cal = Calendar.getInstance(tz);

        long[] ret = {-1, -1};


        yyyy = 2000;
        mm = 01;
        dd = 01;
        hh = 00;
        //from




        cal.set(yyyy, mm - 1, dd, hh, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        from = cal.getTime().getTime();
        ret[0] = from;


        yyyy = 2020;
        mm = 12;
        dd = 31;
        hh = 23;
        //to
        cal.set(yyyy, mm - 1, dd, hh, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        to = cal.getTime().getTime();
        ret[1] = to;

        return ret;
    }




}