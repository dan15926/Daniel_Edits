package edu.upenn.cis350.projectcopy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<User>();
    //"currentUser" should receive the name passed from login
    String currentUser = "Daniel";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeList();
        User user = null;

        //Go through the list of stored users to find User object corresponding to "currentUser"
        //If no such object exists, create one.
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(currentUser)) {
                user = userList.get(i);
                break;
            }
        }
        if (user == null) {
            ArrayList<Course> courses = new ArrayList<Course>();
            courses.add(new Course("Music Theory"));
            User newUser = new User(currentUser, "", courses, "");
            userList.add(newUser);
            user = newUser;
        }

        TextView userWelcome = findViewById(R.id.title_text);
        userWelcome.setText("Welcome Back, " + user.getName() + "!");

        ArrayList<Course> userCourses = user.getCourses();
        Course[] userCoursesArray = new Course[userCourses.size()];
        for (int i = 0; i < userCourses.size(); i++) {
            userCoursesArray[i] = userCourses.get(i);
        }

        //FAB to move to an activity where user can view profile.
        final User finalUser = user;
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("name", finalUser.getName());
                i.putExtra("bio", finalUser.getBio());
                i.putExtra("image", finalUser.getProfileURI());
                startActivityForResult(i, 1);
            }
        });

        recyclerView = findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterCourses(userCoursesArray);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //Dummy Data
    private void initializeList(){
        Course newCourse = new Course("Intro to music theory");
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(newCourse);
        User newUser = new User("Daniel", "", courses, "Hello, my name is Daniel," +
                "I love to do blah blah lbdsadasd asdhasdhasd basdahsdahsdhas dahs da sdhadshasdhahsda" +
                "asdashdasdhasdhahsdhasdhahsdadasvhvvgiggiugugigiugiudgiuasgdiugiugiusadgiuagsi");
        userList.add(newUser);
    }

}
