package com.gustavol95.wappgui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout mRevealView;
    boolean hidden=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mRevealView=(LinearLayout)findViewById(R.id.linearReveal);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.settings && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            // finding X and Y co-ordinates
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = (mRevealView.getTop());

            // to find  radius when icon is tapped for showing layout
            int startradius=0;
            int endradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

            // performing circular reveal when icon will be tapped
            Animator animator = ViewAnimationUtils.createCircularReveal(mRevealView,                     cx, cy, startradius, endradius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(400);

            //reverse animation
            // to find radius when icon is tapped again for hiding layout
            //  starting radius will be the radius or the extent to which circular reveal animation is to be shown

            int reverse_startradius = Math.max(mRevealView.getWidth(),mRevealView.getHeight());

            //endradius will be zero
            int reverse_endradius=0;

            // performing circular reveal for reverse animation
            Animator animate = ViewAnimationUtils.createCircularReveal(mRevealView,cx,cy,reverse_startradius,reverse_endradius);
            if(hidden){

                // to show the layout when icon is tapped
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
            }
            else {
                mRevealView.setVisibility(View.VISIBLE);

                // to hide layout on animation end
                animate.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                animate.start();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
