package com.example.testtracker.view.mealdetails;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testtracker.R;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.models.mealdetails.MealDetails;
import com.example.testtracker.main_app.mealdetails.presenter.MealDetailsPresenterImpl;
import com.example.testtracker.view.adapter.MealDetailsAdapter;
import com.example.testtracker.view.interfaces.MealDetailsView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    private static final String TAG = "MainActivity";
    MealDetailsPresenterImpl presenter;
    MealDetailsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView name, country, category, instructions;
    ImageView mealImage;
    WebView vedio;

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

        // Set up RecyclerView
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MealDetailsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Retrieve passed MealDetails object
        MealDetailsFragmentArgs args = MealDetailsFragmentArgs.fromBundle(getArguments());
        MealDetails.MealsDTO mealDetails = args.getMeal();

        if (mealDetails != null) {
            displayMealDetails(mealDetails);
        }
        if (mealDetails != null && mealDetails.getStrYoutube() != null) {
            loadVideo(mealDetails.getStrYoutube());
        }

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
    public void showMealDetails(MealDetails.MealsDTO meal) {
        
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}