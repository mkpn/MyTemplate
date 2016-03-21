package com.template.ui.activity;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.template.R;
import com.template.databinding.VisibilityObservableListBindingActivityBinding;
import com.template.entity.Item;
import com.template.ui.adapter.ObservableStringListAdapter;

public class VisibilityObservableListBindingActivity extends AppCompatActivity {

    private VisibilityObservableListBindingActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.visibility_observable_list_binding_activity);

        final ObservableArrayList<String> stringList = new ObservableArrayList<>();
        binding.setStringList(stringList);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    Item item = new Item();
                    item.string.set(String.valueOf(i));
                    stringList.add(String.valueOf(i));
                }
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringList.clear();
            }
        });
    }

    @BindingAdapter("stringList")
    public static void setItems(final RecyclerView recyclerView, final ObservableArrayList<String> items){
        if(items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);

        if(recyclerView.getAdapter() == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            ObservableStringListAdapter adapter = new ObservableStringListAdapter(items);
            recyclerView.setAdapter(adapter);
            return;
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
