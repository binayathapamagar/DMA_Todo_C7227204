package com.example.todoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.database.Todo;
import com.example.todoapp.database.TodoRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodosListFragment#} factory method to
 * create an instance of this fragment.
 */
public class TodosListFragment extends Fragment{

    public static TodosListFragment newInstance() {
        return new TodosListFragment();
    }

    public TodosListFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton fab;

    private TodoAdapter adapter;

    private EditText titleEditTExt;
    private EditText descEditText;
    private EditText setDate;
    RadioGroup mRadioGroup;
    private Button submitButton;
    private TodoRepository repository;
    private TextView titleView;

    private List<Todo> mTaskEntries;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_todos_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        titleView = view.findViewById(R.id.title_tv);
        this.adapter = new TodoAdapter(getContext(), new TodoAdapter.TaskCallBack() {
            @Override
            public void onItemDeleted(int id) {

            }

            @Override
            public void onUpdate(Todo todo) {
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab = view.findViewById(R.id.add_btn);

        //Divides a line between each View
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // deprecated, mTodoViewModel = of(this).get(TodoViewModel.class);
        TodoViewModel mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);


        mTodoViewModel.getTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {
                // Update the cached copy of the todos in the adapter.
                adapter.setTodos(todos);
            }
        });

        // ------------------------------------------------Switches to AddTaskFragment-------------------------------------------------------
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, AddTodoFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit();
            }

        });

        // ------------------------------------------------------------------------------------------------------------------------------------

        repository = new TodoRepository(getActivity().getApplication());

    }

}
