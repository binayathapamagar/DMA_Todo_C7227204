package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTodoFragment#} factory method to
 * create an instance of this fragment.
 */
public class AddTodoFragment extends Fragment {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button addButton;
    private Repository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AddTodoFragment newInstance(){
        AddTodoFragment fragment = new AddTodoFragment();
        return  fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_add_todo, container, false);
        titleEditText = view.findViewById(R.id.title_et);
        descriptionEditText = view.findViewById(R.id.description_et);
        addButton = view.findViewById(R.id.add_btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = Repository.getInstance(getActivity().getApplicationContext());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo newTodo = new Todo();
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                newTodo.setTitle(title);
                newTodo.setPriority(1);
                newTodo.setDescription(description);
                repository.addTodo(newTodo);
                Log.d("ADDTODOFRAGMENT", ""+repository.getAllTodos().size());

            }
        });

    }
}