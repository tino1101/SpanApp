package com.tino.spanapp;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TextView textView = findViewById(R.id.text_view);
        String space = " ";
        String prefix = "头条" + space;
        String title = "世卫组织：向中国一线卫生工作者和中国人民表达衷心的祝贺";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(prefix);
        builder.append(space);
        builder.append(title);
        builder.setSpan(new CustomReplacementSpan(textView, R.mipmap.ic_launcher_round, 16, 3, ContextCompat.getColor(this, R.color.span_bg),
                ContextCompat.getColor(this, android.R.color.white), 8, 13), 0, prefix.length() - space.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new CustomClickableSpan(textView, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击头条", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击标题", Toast.LENGTH_SHORT).show();
            }
        }), 0, prefix.length() - space.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }
}