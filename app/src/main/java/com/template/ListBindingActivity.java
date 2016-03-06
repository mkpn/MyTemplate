package com.template;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.template.databinding.ActivityListBindingBinding;
import com.template.model.Item;
import com.template.view.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListBindingActivity extends AppCompatActivity {

    private ActivityListBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_binding);

        final List<Item> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Item item = new Item();
            item.string.set(String.valueOf(i));
            items.add(item);
        }

        binding.setItems(items);
        //微妙かも
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.string.set("追加した要素");
                items.add(item);
                binding.recyclerview.getAdapter().notifyDataSetChanged();
            }
        });

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(3).string.set("変化！");
            }
        });
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<Item> items){
        ItemAdapter adapter = new ItemAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext())); // これ忘れたらダメなんかい！！！！
        recyclerView.setAdapter(adapter);
    }
}
