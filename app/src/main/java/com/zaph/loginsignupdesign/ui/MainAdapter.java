package com.zaph.loginsignupdesign.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zaph.loginsignupdesign.R;
import com.zaph.loginsignupdesign.models.Event;
import com.zaph.loginsignupdesign.models.Student;
import com.zaph.loginsignupdesign.ui.students.StudentsFragment;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DatabaseHelperClass myDb;

    private ArrayList<Event> eventList = new ArrayList();
    private Activity activity;
    private Fragment context;

    public MainAdapter(Activity activity,Fragment context, ArrayList<Event> phoneList){
        this.eventList = phoneList;
        this.context=context;
        this.activity=activity;
    }

    public void setData(ArrayList<Event> eventList){
        this.eventList.clear();
        this.eventList.addAll(eventList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=  LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.event_item_view,parent,false);
        return new MainViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MainViewHolder mainViewHolder=(MainViewHolder) holder;

        mainViewHolder.mainEventDate.setText(eventList.get(position).getEventdate());
        mainViewHolder.mainEventName.setText(eventList.get(position).getEventname());
        mainViewHolder.mainEventVenue.setText(eventList.get(position).getEventvenue());
        mainViewHolder.mainTime.setText(eventList.get(position).getEventtime());
        mainViewHolder.mainRupee.setText(eventList.get(position).getEventfee());

        Glide.with(activity).load(eventList.get(position).getBannerUrl()).into(mainViewHolder.mainEventImage);

       /* mainViewHolder.studentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((MainActivity) context).showMessage(phoneList.get(position).getName(),phoneList.get(position).get_id(),phoneList.get(position).getPhone(),phoneList.get(position).getGender(),phoneList.get(position).getEmail(),phoneList.get(position).getBranch(),phoneList.get(position).getYear());

                ((StudentsFragment) context).showPopup(
                        eventList.get(position).getEventname(),
                        eventList.get(position).getEventcategory(),
                        eventList.get(position).getEventprize(),
                        eventList.get(position).getEventdate(),
                        eventList.get(position).getEventvenue(),
                        eventList.get(position).getEventfee(),
                        eventList.get(position).getHostcourse());
            }
        });*/

        mainViewHolder.mainEventEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,StudentEditor.class);
                intent.putExtra("data2",eventList.get(position));
                context.startActivityForResult(intent,207);
            }
        });

        mainViewHolder.mainEventParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Participants", Toast.LENGTH_SHORT).show();
            }
        });

        mainViewHolder.eventQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,QrCode.class);
                intent.putExtra("dataforqr",eventList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }



    class MainViewHolder extends RecyclerView.ViewHolder{

        private TextView mainEventDate;
        private TextView mainEventName;
        private TextView mainEventVenue;
        private TextView mainTime;
        private TextView mainRupee;
        private ImageView mainEventImage;
        private FloatingActionButton mainEventEdit;
        private Button mainEventParticipants;
        private FloatingActionButton eventQrCode;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            mainEventDate = itemView.findViewById(R.id.mainveventdate);
            mainEventName = itemView.findViewById(R.id.mainveventname);
            mainEventVenue = itemView.findViewById(R.id.maineventvenue);
            mainTime = itemView.findViewById(R.id.maintime);
            mainRupee = itemView.findViewById(R.id.mainrupee);
            mainEventImage = itemView.findViewById(R.id.maineventimage);
            mainEventEdit = itemView.findViewById(R.id.maineventedit);
            mainEventParticipants = itemView.findViewById(R.id.maineventparticipants);

            eventQrCode = itemView.findViewById(R.id.maineventqrcode);
        }
    }


}
