package batson.diabetestrack;

import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CarbListAdapter extends ListAdapter<Carb, DataViewHolder> {
    public CarbListAdapter(@NonNull DiffUtil.ItemCallback<Carb> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        Carb current = getItem(position);
        DateFormat df = new SimpleDateFormat("h:mm a");

        holder.bind(current.getGrams() + " at " + df.format(current.getCreationDate()));

    }

    static class DataDiff extends DiffUtil.ItemCallback<Carb> {

        @Override
        public boolean areItemsTheSame(@NonNull Carb oldItem, @NonNull Carb newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Carb oldItem, @NonNull Carb newItem) {
            return oldItem.getGrams() == newItem.getGrams() && oldItem.getCreationDate().equals(newItem.getCreationDate());
        }
    }
}
