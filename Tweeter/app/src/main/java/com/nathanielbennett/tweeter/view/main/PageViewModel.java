package com.nathanielbennett.tweeter.view.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * Generates a String for displaying on the tabs that have not been implemented yet. This class was
 * generated by the creation of the Tabbed Activity.
 */
@SuppressWarnings("WeakerAccess")
public class PageViewModel extends ViewModel {

    private final MutableLiveData<Integer> index = new MutableLiveData<>();
    private final LiveData<String> text = Transformations.map(index, input -> "Hello world from section: " + input);

    public void setIndex(int index) {
        this.index.setValue(index);
    }

    public LiveData<String> getText() {
        return text;
    }
}