package org.apps.alfalahindia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.pojo.Member;

import java.util.List;

public class MemberListAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater inflater;
    private List<Member> memberList = null;

    private onSelectedEventCalender m_onSelectedEventCalender;

    public MemberListAdapter(Context context, List<Member> memberList,
                             onSelectedEventCalender m_onSelectedEventCalender) {
        super();
        this.context = context;
        this.memberList = memberList;
        this.inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.m_onSelectedEventCalender = m_onSelectedEventCalender;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_member, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_member_profile_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.photo = (ImageView) convertView.findViewById(R.id.item_member_profile_pic);
        holder.textView.setText(memberList.get(position).getName());
        return convertView;
    }

    public interface onSelectedEventCalender {

    }

    public static class ViewHolder {
        TextView textView;
        ImageView photo;
    }

}
