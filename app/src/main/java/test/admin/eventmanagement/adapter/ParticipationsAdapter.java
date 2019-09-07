package test.admin.eventmanagement.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.admin.eventmanagement.Participation;
import test.admin.eventmanagement.R;

public class ParticipationsAdapter extends RecyclerView.Adapter<ParticipationsAdapter.ViewHolder>{

    Context context;
    List<Participation> list = new ArrayList<>();


    public ParticipationsAdapter(Context context, List<Participation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_partic,null,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Participation participation = list.get(i);
        viewHolder.tvCName.setText(participation.getCollegeName());
        viewHolder.tvEName.setText(participation.getEventName());
        viewHolder.tvUName.setText(participation.getUserName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvUName, tvEName, tvCName;
        public ViewHolder(@NonNull View view) {
            super(view);

            tvUName = view.findViewById(R.id.tvUName);
            tvEName = view.findViewById(R.id.tvEName);
            tvCName = view.findViewById(R.id.tvCName);

        }
    }
}
