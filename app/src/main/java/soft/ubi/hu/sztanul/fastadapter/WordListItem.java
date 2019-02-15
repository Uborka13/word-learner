package soft.ubi.hu.sztanul.fastadapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import soft.ubi.hu.sztanul.R;
import soft.ubi.hu.sztanul.persistence.entity.Word;

public class WordListItem extends AbstractItem<WordListItem, WordListItem.WordViewHolder> {

    private Word word;
    private OnDeleteButtonClickListener listener;

    public WordListItem(Word word, OnDeleteButtonClickListener listener) {
        this.word = word;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder getViewHolder(View v) {
        return new WordViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.word_list_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.word_list_item;
    }

    public Word getWord() {
        return word;
    }

    public WordListItem withWord(Word word) {
        this.word = word;
        return this;
    }

    @Override
    public void bindView(WordViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.wordValue.setText(word.getValue());
        holder.wordColor1.setBackgroundColor(Color.parseColor(word.getColor()));
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteButtonClicked(word);
            }
        });
    }

    @Override
    public void unbindView(WordViewHolder holder) {
        super.unbindView(holder);
        holder.wordColor1.setBackground(null);
        holder.wordValue.setText(null);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(Word word);
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.word_color_1)
        TextView wordColor1;
        @BindView(R.id.delete_button)
        ImageView deleteButton;
        @BindView(R.id.word_value)
        TextView wordValue;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
