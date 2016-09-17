package org.apps.alfalahindia.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.pojo.Member;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MemberAdapter extends ArrayAdapter {

    private Context context;

    private List<Member> memberList;

    public MemberAdapter(Context context, int resource, List<Member> memberList) {
        super(context, resource);
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_member, parent, false);

//        Member member = memberList.get(position);
//        System.out.println("here: " + member.toString());
//        TextView tv = (TextView) view.findViewById(R.id.item_member_profile_name);
//        tv.setText(member.getName());

//        if (member.getPhoto() != null) {
//            ImageView image = (ImageView) view.findViewById(R.id.item_member_profile_pic);
//            image.setImageBitmap(member.getPhoto());
//        } else {
//            MemberView container = new MemberView();
//            container.member = member;
//            container.view = view;
//
//            ImageLoader loader = new ImageLoader();
//            // loader.execute(container);
//        }

        return view;
    }

    class MemberView {

        public Member member;
        public View view;
        public Bitmap photo;
    }

    private class ImageLoader extends AsyncTask<MemberView, Void, MemberView> {

        @Override
        protected MemberView doInBackground(MemberView... memberViews) {

            MemberView container = memberViews[0];
            Member member = container.member;

            try {
                String imageUrl = "photos path in website" + member.getPhoto();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap photo = BitmapFactory.decodeStream(in);
                member.setPhoto(photo);
                in.close();
                container.photo = photo;
                return container;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(MemberView memberView) {
//            ImageView image = (ImageView) memberView.view.findViewById(R.id.item_member_profile_pic);
//            image.setImageBitmap(memberView.photo);
//            memberView.member.setPhoto(memberView.photo);
//        }
    }
}
