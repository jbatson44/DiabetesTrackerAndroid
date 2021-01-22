package batson.diabetestrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder extends RecyclerView.ViewHolder {
    private final TextView dataItemView;

    private DataViewHolder(View itemView) {
        super(itemView);
        dataItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text, String hint) {
        dataItemView.setText(text);
        dataItemView.setHint(hint);
    }

    static DataViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DataViewHolder(view);
    }

    public void delete(View view) {

    }
}
