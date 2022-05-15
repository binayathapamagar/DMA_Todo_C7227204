package com.example.todoapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.todoapp.database.Todo;
import com.example.todoapp.database.TodoRepository;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToDoFragment#} factory method to
 * create an instance of this fragment.
 */
public class AddToDoFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText titleEditTExt;
    private EditText descEditText;
    private EditText setDate;
    RadioGroup mRadioGroup;
    private Button submitButton;
    private TodoRepository repository;
    private EditText editDate;
    private ImageView imgDate;
    private DatePickerDialog datePickerDialog;

    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    public static AddToDoFragment newInstance() {
        return new AddToDoFragment();
    }
    public AddToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating the layout
        final View view;
        view = inflater.inflate(R.layout.fragment_add_todo, container, false);

        titleEditTExt = (EditText) view.findViewById(R.id.title_entry);
        descEditText = (EditText) view.findViewById(R.id.desc_entry);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        editDate = (EditText) view.findViewById(R.id.date_edit);
        imgDate = (ImageView) view.findViewById(R.id.imgDate);
        submitButton = (Button) view.findViewById(R.id.submit_btn);

        repository = new TodoRepository(getActivity().getApplication());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditTExt.getText().toString().trim();
                String desc = descEditText.getText().toString().trim();
                int priority = getPriorityFromViews();
                String date = editDate.getText().toString().trim();
                if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Empty Fields!")
                            .setMessage("All of the fields are required.")
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {  
                                    return;
                                }
                            }).show();
                } else {
                    Todo todo = new Todo(title, desc, priority, date);
                    repository.insert(todo);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, TodosListFragment.newInstance())
                            .commitNow();
                }
            }

            //Method to get priority
            private int getPriorityFromViews() {
                int priority = 1;
                int checkedId = ((RadioGroup) view.findViewById(R.id.radioGroup)).getCheckedRadioButtonId(); //Made view final

                switch (checkedId) {
                    case R.id.radButton1:
                        priority = PRIORITY_HIGH;
                        break;
                    case R.id.radButton2:
                        priority = PRIORITY_MEDIUM;
                        break;
                    case R.id.radButton3:
                        priority = PRIORITY_LOW;
                        break;
                }
                return priority;
            }
            //Method to set Priority
            private void setPriorityInViews(int priority) {
                switch (priority) {
                    case PRIORITY_HIGH:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton1);
                        break;
                    case PRIORITY_MEDIUM:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton2);
                        break;
                    case PRIORITY_LOW:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton3);
                }

            }
        });

        // View and set Calendar
        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        editDate.setText(day + "/" + (month) + "/" + year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        return view;
    }
}