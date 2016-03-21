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
import com.template.databinding.ObservableListBindingActivityBinding;
import com.template.entity.Item;
import com.template.ui.adapter.ObservableStringListAdapter;

public class ObservableListBindingActivity extends AppCompatActivity {

    private ObservableListBindingActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.observable_list_binding_activity);

        final ObservableArrayList<String> itemList = new ObservableArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Item item = new Item();
            item.string.set(String.valueOf(i));
            itemList.add(String.valueOf(i));
        }

        binding.setItems(itemList);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.string.set("追加した要素");
                itemList.add("追加した要素");
                binding.recyclerview.getAdapter().notifyDataSetChanged();
            }
        });

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.set(3, "変化！");
            }
        });
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, ObservableArrayList<String> items){
        if(recyclerView.getAdapter() == null){
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            ObservableStringListAdapter adapter = new ObservableStringListAdapter(items);
            recyclerView.setAdapter(adapter);
            return;
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
