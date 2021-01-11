package batson.diabetestrack;

import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class DataListAdapter extends ListAdapter<BloodSugar, DataViewHolder> {
    public DataListAdapter(@NonNull DiffUtil.ItemCallback<BloodSugar> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        BloodSugar current = getItem(position);
        DateFormat df = new SimpleDateFormat("h:mm a");
        holder.bind(current.getBsLevel() + " at " + df.format(current.getCreationDate()));
    }

    static class DataDiff extends DiffUtil.ItemCallback<BloodSugar> {

        @Override
        public boolean areItemsTheSame(@NonNull BloodSugar oldItem, @NonNull BloodSugar newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BloodSugar oldItem, @NonNull BloodSugar newItem) {
            return oldItem.getBsLevel() == newItem.getBsLevel() && oldItem.getCreationDate().equals(newItem.getCreationDate());
        }
    }
}
