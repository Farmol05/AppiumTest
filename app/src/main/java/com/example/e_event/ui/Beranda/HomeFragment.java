package com.example.e_event.ui.Beranda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.example.e_event.Event;
import com.example.e_event.EventDetailActivity;
import com.example.e_event.R;
import com.example.e_event.User;

import java.util.List;

public class HomeFragment extends Fragment {
    private DataEvent event;

    private HomeViewModel homeViewModel;
    private int blue = (Color.rgb(50,150,200));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final LinearLayout parent = root.findViewById(R.id.parent);
        event = new DataEvent(getActivity());
        List<Event> events = event.getAllEvent();
        Bitmap poster;
        byte[] b;
        Drawable customInput = ResourcesCompat.getDrawable(getResources(), R.drawable.custom_input_event, null);
        if(events.isEmpty()){
            TextView empty = new TextView(getActivity());
            empty.setText("Belum ada event apapun");
            empty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            empty.setPadding(0,30,0,0);
            parent.addView(empty);
        }else {
            for (Event e : events) {

                b = e.getPoster();
                poster = BitmapFactory.decodeByteArray(b, 0, b.length);
                poster = Bitmap.createScaledBitmap(poster, 200, 240, true);

                LinearLayout hori = new LinearLayout(getActivity());
                hori.setOrientation(LinearLayout.HORIZONTAL);
                hori.setBackground(customInput);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 12, 0, 6);
                hori.setLayoutParams(params);
                parent.addView(hori);

                ImageView image = new ImageView(getActivity());
                image.setImageBitmap(poster);
//            image.setBackground(customImg);
                hori.addView(image);
                image.setPadding(24, 6, 12, 6);
                image.getLayoutParams().width = 200;
                image.getLayoutParams().height = 240;

                LinearLayout ver = new LinearLayout(getActivity());
                ver.setPadding(6, 12, 10, 6);
                ver.setOrientation(LinearLayout.VERTICAL);

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
                        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                        intent.putExtra("eventID", e.getId());
                        startActivity(intent);

                    }
                });
            }
        }
          return root;
    }
}