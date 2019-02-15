package soft.ubi.hu.sztanul.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import soft.ubi.hu.sztanul.R;
import soft.ubi.hu.sztanul.persistence.entity.Word;
import soft.ubi.hu.sztanul.viewmodels.ViewModelFactory;
import soft.ubi.hu.sztanul.viewmodels.WordViewModel;

public class WordActivity extends AppCompatActivity {

    @BindView(R.id.settings_image_view)
    ImageView settings;
    @BindView(R.id.word_holder)
    TextView wordHolder;
    private List<Word> wordsList = new ArrayList<>();
    private boolean started = false;
    private WordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        ButterKnife.bind(this);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication());
        viewModel = viewModelFactory.create(WordViewModel.class);
    }

    @OnClick(R.id.settings_image_view)
    public void settingsPressed() {
        Intent intent = new Intent(this, WordsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.word_holder)
    public void wordHolderPressed() {
        viewModel.getAllWords().observe(this, words -> {
            if (!words.isEmpty()) {
                Random random = new Random();
                int i = random.nextInt(words.size());
                wordHolder.setText(words.get(i).getValue());
                wordHolder.setTextColor(Color.parseColor(words.get(i).getColor()));
            }
        });
    }
}
