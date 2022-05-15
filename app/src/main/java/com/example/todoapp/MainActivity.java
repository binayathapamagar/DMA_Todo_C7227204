package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.todoapp.database.Todo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TodosListFragment fragment = new TodosListFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container, fragment).commit();
    }

    public void moveToUpdate(Todo todo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("todo",todo);
        Fragment fragment = new UpdateFragment().newInstance();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
    }

}