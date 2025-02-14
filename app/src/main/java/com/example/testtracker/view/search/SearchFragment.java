package com.example.testtracker.view.search;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.testtracker.R;
import com.example.testtracker.models.allcategory.Category;
import com.example.testtracker.models.allcountries.Country;
import com.example.testtracker.models.mealdetails.AllIngrediants;
import com.example.testtracker.network.PlannerRemoteDataSource;
import com.example.testtracker.presenter.intefaces.SearchPresenter;
import com.example.testtracker.presenter.search.SearchPresenterImpl;
import com.example.testtracker.view.adapter.SearchAdapter;
import com.example.testtracker.view.catandcountrymeals.CategoriesMealsFragmentDirections;
import com.example.testtracker.view.interfaces.SearchViewinter;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class SearchFragment extends Fragment implements SearchViewinter {

    ChipGroup chipsGroup;
    SearchAdapter adapter;
    PlannerRemoteDataSource remoteDataSource;
    private SearchPresenter presenter;
    private GridView gridView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SearchView searchView;

    private List<Object> allData = new ArrayList<>(); // Combined list for all data
    private PublishSubject<String> searchSubject = PublishSubject.create(); // RxJava subject for search queries


    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.searchgridview);
        chipsGroup = view.findViewById(R.id.chipsgroup);
        searchView = view.findViewById(R.id.searchView);
        remoteDataSource = PlannerRemoteDataSource.getInstance();
        presenter = new SearchPresenterImpl(this, remoteDataSource);
        chipsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                presenter.onChipSelected(checkedId);
            }
        });
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        searchSubject.onNext(newText);
                        return true;
                    }
                }
        );
        // Subscribe to search queries and filter data
        compositeDisposable.add(
                searchSubject
                        .debounce(300, java.util.concurrent.TimeUnit.MILLISECONDS) // Debounce to avoid rapid updates
                        .switchMap(query -> Observable.fromIterable(allData)
                                .filter(item -> {
                                    if (item instanceof AllIngrediants.Ingrediants) {
                                        return ((AllIngrediants.Ingrediants) item).getStrIngredient().toLowerCase().contains(query.toLowerCase());
                                    } else if (item instanceof Category) {
                                        return ((Category) item).getStrCategory().toLowerCase().contains(query.toLowerCase());
                                    } else if (item instanceof Country) {
                                        return ((Country) item).getStrArea().toLowerCase().contains(query.toLowerCase());
                                    }
                                    return false;
                                })
                                .toList()
                                .toObservable()
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(filteredList -> {
                            adapter = new SearchAdapter(getContext(), filteredList);
                            gridView.setAdapter(adapter);
                        }, throwable -> {
                            // Handle error
                        })
        );

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Object item = adapter.getItem(position);
            if (item instanceof Country) {
                String countryName = ((Country) item).getStrArea();
                Log.d("MainActivity", "Clicked on Country: " + countryName);
                navigateToCategoriesMealsFragment(countryName, true);
            } else if (item instanceof Category) {
                String categoryName = ((Category) item).getStrCategory();
                Log.d("MainActivity", "Clicked on Category: " + categoryName);
                navigateToCategoriesMealsFragment(categoryName, false);
            } else if (item instanceof AllIngrediants.Ingrediants) {
                String ingredientName = ((AllIngrediants.Ingrediants) item).getStrIngredient();
                Log.d("MainActivity", "Clicked on Ingredient: " + ingredientName);
                navigateToCategoriesMealsFragment(ingredientName, false);
            }
        });

    }

    private void navigateToCategoriesMealsFragment(String name, boolean isCountry) {
        SearchFragmentDirections.ActionSearchFragmentToCategoriesMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToCategoriesMealsFragment(name);
        if(isCountry){
            action.setIsCountry(true);
        }else{
            action.setIsCountry(false);
        }
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void showIngredients(List<AllIngrediants.Ingrediants> ingredients) {
        allData.addAll(ingredients);
        adapter = new SearchAdapter(getContext(), ingredients);
        gridView.setAdapter(adapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        allData.addAll(categories);
        adapter = new SearchAdapter(getContext(), categories);
        gridView.setAdapter(adapter);
    }

    @Override
    public void showCountries(List<Country> countries) {
        allData.addAll(countries);
        adapter = new SearchAdapter(getContext(), countries);
        gridView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {

    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Dispose of all subscriptions in the fragment
//        compositeDisposable.dispose();
//        // Dispose of all subscriptions in the presenter
//        presenter.dispose();
//    }
}