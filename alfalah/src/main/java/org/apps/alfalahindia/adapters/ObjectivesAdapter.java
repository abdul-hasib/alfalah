package org.apps.alfalahindia.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apps.alfalahindia.R;

public class ObjectivesAdapter extends PagerAdapter {

    Object[][] mResources = {
            {R.drawable.ic_team_alif, "Team AlFalah India"},
            {R.drawable.ic_coaching, "Educational facilities to deserving students"},
            {R.drawable.ic_people, "Financial support for poor and needy people"},
            {R.drawable.ic_business, "Help people to setup small scale business"},
            {R.drawable.ic_students, "Financial support for student's education"},
            {R.drawable.ic_zakat, "Encourage girl student's education"}
    };
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ObjectivesAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.objectives_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource((Integer) mResources[position][0]);

        TextView imageDescription = (TextView) itemView.findViewById(R.id.imageDescription);
        imageDescription.setText((CharSequence) mResources[position][1]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
