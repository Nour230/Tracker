package com.example.testtracker.view.mealdetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.presenter.mealdetails.MealDetailsPresenterImpl;
import com.example.testtracker.view.adapter.MealDetailsAdapter;
import com.example.testtracker.view.interfaces.MealDetailsView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private static final String TAG = "MainActivity";
    MealDetailsPresenterImpl presenter;
    MealDetailsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView name, country, category, instructions;
    ImageView mealImage;
    WebView vedio;
    MaterialButton fav, plan;
    SharedPreferences sharedPreferences;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind views
        name = view.findViewById(R.id.mealName);
        country = view.findViewById(R.id.country);
        category = view.findViewById(R.id.caregory);
        instructions = view.findViewById(R.id.instructions);
        mealImage = view.findViewById(R.id.IMmealdetais);
        recyclerView = view.findViewById(R.id.ingrediantrec);
        vedio = view.findViewById(R.id.vedio);
        fav = view.findViewById(R.id.detailsaddtofav);
        plan = view.findViewById(R.id.detailsaddtoplan);

        // Set up RecyclerView
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MealDetailsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        presenter = new MealDetailsPresenterImpl(this,
                MealDetailsRepositoryImpl.getInstance(getContext()));

        // Retrieve passed MealDetails object
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealDetails.MealsDTO mealDetails = args.getMeal();

        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", null);
        SavedMeals favMeal = new SavedMeals(mealDetails.getIdMeal(), userId, "fav", mealDetails, true, false);

        if (mealDetails != null) {
            displayMealDetails(mealDetails);
        }
        if (mealDetails != null && mealDetails.getStrYoutube() != null) {
            loadVideo(mealDetails.getStrYoutube());
        }
        fav.setOnClickListener(v -> {
            presenter.addToFav(favMeal);
        });

        plan.setOnClickListener(v -> showDatePicker());


    }

    private void showDatePicker() {
        // Get the current date and time in the Cairo timezone
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));

        // Set the start of the range to today (in UTC milliseconds)
        long startOfRange = calendar.getTimeInMillis();

        // Set the end of the range to 7 days from today (in UTC milliseconds)
        calendar.add(Calendar.DAY_OF_YEAR, 6); // Add 7 days to the current date
        long endOfRange = calendar.getTimeInMillis();

        // Create a calendar constraint to allow only dates from today to 7 days from today
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(startOfRange); // Start from today
        constraintsBuilder.setEnd(endOfRange); // End at 7 days from today
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= startOfRange && date <= endOfRange; // Only allow dates within the range
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel parcel, int i) {
            }
        });

        // Create the date picker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a day (today to 7 days from now)")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(startOfRange) // Default selection: today (in UTC milliseconds)
                .build();

        // Show the date picker
        datePicker.show(getParentFragmentManager(), "DATE_PICKER");

        // Handle the selected date
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert the selected date to a readable format
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            selectedCalendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = format.format(selectedCalendar.getTime());

            // Notify the user
            Toast.makeText(getContext(), "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Call the presenter's addToPlan method with the selected date
            MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
            MealDetails.MealsDTO mealDetails = args.getMeal();
            sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("id", null);
            SavedMeals planMeal = new SavedMeals(mealDetails.getIdMeal(), userId, selectedDate, mealDetails, false, true);

            if (mealDetails != null) {
                presenter.addToPlan(planMeal); // Pass the meal details and selected date
            }
        });
    }    private void loadVideo(String videoUrl) {
        // Extract YouTube video ID
        String videoId = videoUrl.substring(videoUrl.lastIndexOf("=") + 1);
        String embedUrl = "https://www.youtube.com/embed/" + videoId;

        // Enable JavaScript
        WebSettings webSettings = vedio.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load URL inside WebView
        vedio.setWebViewClient(new WebViewClient());
        vedio.loadUrl(embedUrl);
    }

    private void displayMealDetails(MealDetails.MealsDTO mealDetails) {

        name.setText(mealDetails.getStrMeal());
        country.setText(mealDetails.getStrArea());
        category.setText(mealDetails.getStrCategory());
        instructions.setText(mealDetails.getStrInstructions());

        Glide.with(this).load(mealDetails.getStrMealThumb()).into(mealImage);


        List<AllIngrediants.Ingrediants> ingredientsList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ingredient = getFieldValue(mealDetails, "getStrIngredient" + i);
            String measure = getFieldValue(mealDetails, "getStrMeasure" + i);
            if (ingredient != null && !ingredient.isEmpty()) {
                AllIngrediants.Ingrediants ingredientObj = new AllIngrediants.Ingrediants();
                ingredientObj.setStrIngredient(ingredient);
                ingredientObj.setIdIngredient(measure);
                ingredientsList.add(ingredientObj);
            }
        }


        adapter.updateData(ingredientsList);
    }


    private String getFieldValue(MealDetails.MealsDTO mealDetails, String methodName) {
        try {
            Method method = mealDetails.getClass().getMethod(methodName);
            return (String) method.invoke(mealDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void showMealDetails(MealDetails.MealsDTO meal) {

    }

    @Override
    public void addToFav() {
        fav.setText("Added to Fav");
        Log.i(TAG, "addToFav: ");
        Toast.makeText(getContext(), "Added to Fav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addToPlan() {
        fav.setText("Added to Plan");
        Log.i(TAG, "addToPlan: ");
        Toast.makeText(getContext(), "Added to Plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}