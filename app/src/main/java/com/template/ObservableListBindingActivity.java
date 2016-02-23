package com.template;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.template.databinding.ObservableListBindingActivityBinding;
import com.template.model.Item;
import com.template.model.ObservableItems;
import com.template.view.adapter.ItemAdapter;
import com.template.view.adapter.ObservableItemAdapter;

public class ObservableListBindingActivity extends AppCompatActivity {

    private ObservableListBindingActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.observable_list_binding_activity);

        final ObservableArrayList<Item> itemList = new ObservableArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.string.set(String.valueOf(i));
            itemList.add(item);
        }

        final ObservableItems items = new ObservableItems(itemList);
        binding.setItems(items);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.string.set("追加した要素");
                items.items.add(item);
                binding.recyclerview.getAdapter().notifyDataSetChanged();
            }
        });

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.items.get(3).string.set("変化！");
            }
        });
    }
}
