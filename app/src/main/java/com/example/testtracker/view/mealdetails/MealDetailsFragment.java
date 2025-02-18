package com.example.testtracker.view.mealdetails;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.db.SavedMeals;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.models.mealdetails.MealDetailsRepositoryImpl;
import com.example.testtracker.presenter.mealdetails.MealDetailsPresenterImpl;
import com.example.testtracker.utils.DateUtile;
import com.example.testtracker.view.adapter.MealDetailsAdapter;
import com.example.testtracker.view.interfaces.MealDetailsView;
import com.example.testtracker.view.interfaces.OnDateSelectedListener;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView, OnDateSelectedListener {
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
                MealDetailsRepositoryImpl.getInstance(getContext()), getContext());

        // Retrieve passed MealDetails object
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealDetails.MealsDTO mealDetails = args.getMeal();
        boolean isPlan = args.getIsPlan();
        if (isPlan) {
            plan.setText("Added to Plan");
            plan.setEnabled(false);
        }
        boolean isFav = args.getIsFav();
        if (isFav) {
            fav.setText("Added to Fav");
            fav.setEnabled(false);
        }

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
            presenter.sendData(favMeal);
        });

        plan.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            Context context = getContext();
            DateUtile.showDatePicker(fragmentManager, context, this);
        });
    }

    private void loadVideo(String videoUrl) {
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
    public void addToFav() {
        fav.setText("Added to Fav");
        fav.setEnabled(false);
    }

    @Override
    public void addToPlan() {
        plan.setText("Added to Plan");
        plan.setEnabled(false);
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDateSelected( String formattedDate) {
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealDetails.MealsDTO mealDetails = args.getMeal();
        sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", null);
        SavedMeals planMeal = new SavedMeals(mealDetails.getIdMeal(), userId, formattedDate, mealDetails, false, true);

        if (mealDetails != null) {
            presenter.addToPlan(planMeal); // Pass the meal details and selected date
            presenter.sendData(planMeal);
        }
    }

}