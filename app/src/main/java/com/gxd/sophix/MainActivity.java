package com.gxd.sophix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView tvCurrentVersion;

    private Button btnShowToast;

    private Button btnLoadPatch;

    private Button btnKillSelf;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initText();
    }

    private void initView ()
    {

        tvCurrentVersion = (TextView) findViewById(R.id.tvVersion);
        btnShowToast = (Button) findViewById(R.id.btnShowToast);
        btnShowToast.setOnClickListener(this);
        btnKillSelf = (Button) findViewById(R.id.btnKillSelf);
        btnKillSelf.setOnClickListener(this);
    }

    private void initText ()
    {

        //tvCurrentVersion.setText("我是基准包");
        tvCurrentVersion.setText("我是patch包");
    }

    @Override
    public void onClick (View v)
    {

        switch (v.getId())
        {
            // 测试热更新功能
            case R.id.btnShowToast:
                testToast();
                break;
            // 杀死进程
            case R.id.btnKillSelf:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
    }

    public void testToast ()
    {

        Toast.makeText(this, LoadBugClass.getBugString(), Toast.LENGTH_SHORT).show();
    }
}
