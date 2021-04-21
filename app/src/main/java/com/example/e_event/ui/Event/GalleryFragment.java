package com.example.e_event.ui.Event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_event.DataEvent;
import com.example.e_event.DataUser;
import com.example.e_event.EditEventActivity;
import com.example.e_event.Event;
import com.example.e_event.R;
import com.example.e_event.User;
import com.example.e_event.ui.Beranda.HomeViewModel;

import java.util.List;

public class GalleryFragment extends Fragment {

    private DataEvent event;
    private DataUser db_user;
    private User authUser;

    private HomeViewModel homeViewModel;
    private int blue = (Color.rgb(50,150,200));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final LinearLayout parent = root.findViewById(R.id.parent);
        event = new DataEvent(getActivity());
        db_user = new DataUser(getContext());
        db_user.open();
        authUser = db_user.getLoggedInUser();
        int user_id = authUser.getId();
        List<Event> events = event.getAllEvent(user_id);
        Bitmap poster;
        byte[] b;
        Drawable customInput = ResourcesCompat.getDrawable(getResources(), R.drawable.custom_input_event, null);
        if(events.isEmpty()){
            TextView empty = new TextView(getActivity());
            empty.setText("Anda belum membuat event apapun\n\nKlik tombol + di bawah untuk membuat event");
            empty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            empty.setPadding(0,30,0,0);
            Log.d("EMP", "Kosong!");
            parent.addView(empty);
        }else {
            for(Event e : events){
                b = e.getPoster();
                poster = BitmapFactory.decodeByteArray(b, 0, b.length);
                poster = Bitmap.createScaledBitmap(poster, 200, 240, true);

                LinearLayout hori = new LinearLayout(getActivity());
                hori.setOrientation(LinearLayout.HORIZONTAL);
                hori.setBackground(customInput);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0,12,0,6);
                hori.setLayoutParams(params);
                parent.addView(hori);

                ImageView image = new ImageView(getActivity());
                image.setImageBitmap(poster);
                hori.addView(image);
                image.setPadding(24, 6, 12, 6);
                image.getLayoutParams().width = 200;
                image.getLayoutParams().height = 240;

                LinearLayout ver = new LinearLayout(getActivity());
                ver.setOrientation(LinearLayout.VERTICAL);
                ver.setPadding(6, 12, 10, 6);
                hori.addView(ver);

                TextView title = new TextView(getActivity());
                title.setText(e.getTitle());
                title.setTextSize(16);
                title.setTextColor(blue);
                ver.addView(title);

                TextView date = new TextView(getActivity());
                date.setText("Tanggal: " + e.getDate_started());
                date.setPadding(0, 12, 0, 0);
                ver.addView(date);

                hori.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EditEventActivity.class);
                        intent.putExtra("id", e.getId());
                        startActivity(intent);
                    }
                });
            }
        }
        return root;
    }
}