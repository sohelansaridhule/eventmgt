package test.admin.eventmanagement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder> {


    private ArrayList<Event> eventList;
    private final Context mContext;

    public EventAdapter(Context context,ArrayList<Event> eventList)
    {
        this.mContext = context;
        this.eventList=eventList;


    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.event_items, null, false);

        return new CustomViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int i) {

        final Event event  = eventList.get(i);
        holder.tv_title.setText(eventList.get(i).getEventName());
        holder.tv_date.setText(eventList.get(i).getEventDate());
        if (!event.getEventImg().equals(""))
            holder.imgPost.setImageURI(Uri.parse(event.getEventImg()));
        else
            holder.imgPost.setImageDrawable(mContext.getResources().getDrawable(R.drawable.event1));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EventDetails.class);
                intent.putExtra("title",event.getEventName());
                intent.putExtra("date",event.getEventDate());
                intent.putExtra("caption",event.getEventCaption());
                intent.putExtra("imagePath",event.getEventImg());

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder
    {

        final TextView tv_title,tv_date;
        final ImageView imgPost,imgDelete;



        CustomViewHolder(View view)
        {
            super(view);
            this.tv_title = view.findViewById(R.id.txtEventName);
            this.tv_date = view.findViewById(R.id.txtEventDate);

            this.imgPost = view.findViewById(R.id.imgEvent);
            this.imgDelete = view.findViewById(R.id.imageDelete);
        }
    }

}
