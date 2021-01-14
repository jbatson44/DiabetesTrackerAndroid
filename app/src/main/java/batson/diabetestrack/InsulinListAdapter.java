package batson.diabetestrack;

import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class InsulinListAdapter extends ListAdapter<Insulin, DataViewHolder> {
    public InsulinListAdapter(@NonNull DiffUtil.ItemCallback<Insulin> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Insulin current = getItem(position);
        DateFormat df = new SimpleDateFormat("h:mm a");

        holder.bind(current.getUnits() + " at " + df.format(current.getCreationDate()));

    }

    static class DataDiff extends DiffUtil.ItemCallback<Insulin> {

        @Override
        public boolean areItemsTheSame(@NonNull Insulin oldItem, @NonNull Insulin newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Insulin oldItem, @NonNull Insulin newItem) {
            return oldItem.getUnits() == newItem.getUnits() && oldItem.getCreationDate().equals(newItem.getCreationDate());
        }
    }
}
