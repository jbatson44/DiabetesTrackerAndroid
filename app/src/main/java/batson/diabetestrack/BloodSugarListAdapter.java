package batson.diabetestrack;

import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class BloodSugarListAdapter extends ListAdapter<BloodSugar, DataViewHolder> {
    public BloodSugarListAdapter(@NonNull DiffUtil.ItemCallback<BloodSugar> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DataViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        BloodSugar current = getItem(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("M/d/yy h:mm a",
                Locale.ENGLISH);
        holder.bind(formatter.format(current.getCreationDate()) + ": " + current.getBsLevel()
                + " blood sugar level.", current.getID() + " " + "B");
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
