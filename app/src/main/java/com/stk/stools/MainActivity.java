package com.stk.stools;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.os.SystemProperties;
import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stk.stools.Utils.HttpTools;
import com.stk.stools.Utils.Tools;
import com.stk.stools.Utils.UpdateInfo;
import com.stk.stools.Utils.AssetCopyer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvversion_msg;
    private Button btck;
    private RelativeLayout rl;
    private String flag = "check";//按钮检测标志
    private String mWay = null;//星期
    private String weibo_url = "http://weibo.com/Starstok"; //下载更新定位的网址，可以写在github(weibo_url)
    private String strlatest = SystemProperties.get("ro.ota_version");  //最新版本
    private String strcurrent = SystemProperties.get("ro.ota_version");  //本地版本
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvversion_msg = (TextView) findViewById(R.id.version_msg);
        btck = (Button) findViewById(R.id.buttonck);
        rl = (RelativeLayout) findViewById(R.id.topLayout);

        tvversion_msg.setText(getResources().getString(R.string.current_version) + strcurrent);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getResources().getString(R.string.check_update), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // TODO Auto-generated method stub

                if (flag.equals("check")) {

                    check checktask = new check();
                    checktask.execute();


                } else if (flag.equals("disk")) {

                    Uri uri = Uri.parse(weibo_url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }


            }
        });

        //更新日志
        rl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if (strlatest == strcurrent || strlatest == null) //为空或为当前版本显示当前日志
                {
                    Intent it = new Intent(MainActivity.this, UpdateInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("version", strcurrent);
                    it.putExtras(bundle);
                    startActivity(it);

                } else {
                    Intent it = new Intent(MainActivity.this, UpdateInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("version", strlatest);
                    it.putExtras(bundle);
                    startActivity(it);

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        
    }

    public void onStart(){
        super.onStart();

        // 在程序启动时复制数据到SD卡
        AssetCopyer asset = new AssetCopyer(getBaseContext());
        try {
            asset.copy();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MainActivity.this, Tools.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//后台执行，防止卡死
private class check extends AsyncTask<Object, Integer, Integer>
{


    Button btck = (Button) findViewById(R.id.buttonck);
    @Override

    protected void onPreExecute()
    {
        btck.setText("检测中。。。");
    }

    // 在后台运行


    /**
     * 这里的Object参数对应AsyncTask中的第一个参数
     * 这里的Int返回值对应AsyncTask的第三个参数
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
     */


    @Override
    protected Integer doInBackground(Object... arg0)
    {
        // TODO Auto-generated method stub
        return checkupdate();

    }

    @Override
    protected void onPostExecute(Integer result)
    {

        switch (result)
        {
            case 0:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.isnew), Toast.LENGTH_SHORT).show();
                btck.setText("检测更新");
                break;
            case 1:
                tvversion_msg.setText("最新版本:" + strlatest);
                flag = "disk";
                Toast.makeText(MainActivity.this, getResources().getString(R.string.nisnew), Toast.LENGTH_SHORT).show();
                btck.setText("下载更新");
                break;
            case 2:
                Toast.makeText(MainActivity.this, getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
                btck.setText("检测更新");
                break;
        }
    }

    /**
     * 这里的Intege参数对应AsyncTask中的第二个参数
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
     */
    @Override
    protected void onProgressUpdate(Integer... values)
    {

    }


}

    public int checkupdate()
    {
        int tmp;
        try
        {
            strlatest = HttpTools.getcontent(getResources().getString(R.string.base_url) + "version"); //获取版本号
            weibo_url = HttpTools.getcontent(getResources().getString(R.string.base_url) + "ota_url"); //获取链接


            if(strcurrent.equals(strlatest))
            {
                tmp = 0;
            }
            else
            {
                tmp = 1;
            }
        }
        catch (Exception e)
        {
            tmp = 2;
        }
        return tmp;

    }


}
