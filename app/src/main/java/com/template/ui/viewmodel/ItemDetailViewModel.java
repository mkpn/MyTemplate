package com.template.ui.viewmodel;

import android.databinding.ObservableField;

import com.template.service.ItemService;

/**
 * Created by makoto on 2016/02/15.
 */
public class ItemDetailViewModel {

    public ObservableField<String> description;

    public ItemDetailViewModel() {
        ItemService service = new ItemService();
        this.description = new ObservableField<>();
        this.description.set(service.getDescription());
    }
}
