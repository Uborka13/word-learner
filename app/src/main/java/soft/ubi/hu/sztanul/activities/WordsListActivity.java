package soft.ubi.hu.sztanul.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soft.ubi.hu.sztanul.R;
import soft.ubi.hu.sztanul.fastadapter.WordListItem;
import soft.ubi.hu.sztanul.persistence.entity.Word;
import soft.ubi.hu.sztanul.viewmodels.ViewModelFactory;
import soft.ubi.hu.sztanul.viewmodels.WordViewModel;

public class WordsListActivity extends AppCompatActivity implements WordListItem.OnDeleteButtonClickListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private FastItemAdapter<WordListItem> itemAdapter;
    private WordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_list);
        ButterKnife.bind(this);
        ViewModelFactory factory = new ViewModelFactory(getApplication());
        viewModel = factory.create(WordViewModel.class);
        itemAdapter = new FastItemAdapter<>();
        itemAdapter.withSelectable(false);
        itemAdapter.withMultiSelect(false);
        itemAdapter.withSelectOnLongClick(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        swipeRefresh.setOnRefreshListener(() -> viewModel.getAllWords().observe(WordsListActivity.this, words -> {
            List<WordListItem> wordListItems = new ArrayList<>();
//                    wordListItems = words.stream().map(WordListItem::new).collect(Collectors.toList());
            for (Word word : words) {
                wordListItems.add(new WordListItem(word, this));
            }
            itemAdapter.clear();
            itemAdapter.add(wordListItems);
            swipeRefresh.setRefreshing(false);
        }));
        viewModel.getAllWords().observe(this, words -> {
            List<WordListItem> wordListItems = new ArrayList<>();
//                    wordListItems = words.stream().map(WordListItem::new).collect(Collectors.toList());
            for (Word word : words) {
                wordListItems.add(new WordListItem(word, this));
            }
            itemAdapter.clear();
            itemAdapter.add(wordListItems);
        });
    }

    @OnClick(R.id.fab_button)
    public void onFabButtonPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Szó hozzáadása");
        View view = LayoutInflater.from(this).inflate(R.layout.custom_input_dialog, this.findViewById(android.R.id.content), false);
        final EditText input = view.findViewById(R.id.input);
        final RadioGroup group = view.findViewById(R.id.radio_color_group);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            if (!input.getText().toString().isEmpty()) {
                String text = input.getText().toString();
                String color = "";
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.blue:
                        color = "#2196F3";
                        break;
                    case R.id.red:
                        color = "#f44336";
                        break;
                    case R.id.green:
                        color = "#4caf50";
                        break;
                    case R.id.yellow:
                        color = "#ffeb3b";
                        break;
                    case R.id.purple:
                        color = "#9c27b0";
                        break;
                    case R.id.gray:
                        color = "#607d8b";
                        break;
                }
                viewModel.saveWord(new Word().withValue(text).withColor(color));

            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void onDeleteButtonClicked(Word word) {
        viewModel.deleteWord(word);
    }
}
