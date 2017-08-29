package com.xiaoma.animatorrecyclerhelper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoma.animatorrecyclerhelper.animator.IAnimatorListener;
import com.xiaoma.animatorrecyclerhelper.strategy.BottomStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.LeftStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.RightStrategy;
import com.xiaoma.animatorrecyclerhelper.strategy.TopStrategy;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new A());
        new AnimatorHelper()
                .setRatio(0.5f)
                .setAutoScroll(true)
                .setScrollOnePage(true)
                .setType(AnimatorHelper.LEFT)
                .setListener(new IAnimatorListener() {
                    @Override
                    public void setAnimator(float distance, float percent, View view) {
                        view.setScaleY(1 - percent);
                        view.setAlpha(1 - percent);
                    }

                    @Override
                    public void resetViewToNormal(View view) {
                        view.setScaleY(1);
                        view.setAlpha(1);
                    }
                })
                .attachToRecyclerView(rv);
    }


    class A extends RecyclerView.Adapter<A.VH> {


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setBackgroundColor(position % 2 == 0 ? Color.RED : Color.YELLOW);
            holder.tv.setTextColor(position % 2 == 0 ? Color.YELLOW : Color.RED);
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class VH extends RecyclerView.ViewHolder {
            private TextView tv;

            public VH(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }
}
