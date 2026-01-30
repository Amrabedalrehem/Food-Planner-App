package com.hammi.foodplanner.ui.view.categorries;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.android.material.snackbar.Snackbar;
import com.hammi.foodplanner.R;
import com.hammi.foodplanner.data.models.remote.Category;
import com.hammi.foodplanner.ui.presentation.Categories.CategoriesContract;
import com.hammi.foodplanner.ui.presentation.Categories.CategoriesPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class allcategories extends Fragment implements CategoriesContract.View {
    private Dialog loadingDialog;
    private CategoriesPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView imageViewBack;
    private AllCategoriesAdapter adapter;
    private List<Category> categoriesList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.rvAllCategories);
        imageViewBack = view.findViewById(R.id.allCategoriesBack);
        adapter = new AllCategoriesAdapter(categoriesList);

        GridLayoutManager layoutManager =
                new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter = new CategoriesPresenter(this);
        presenter.getCategories();
         BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
         navBar.getMenu().setGroupCheckable(0, true, false);
        for (int i = 0; i < navBar.getMenu().size(); i++) {
            navBar.getMenu().getItem(i).setChecked(false);
        }
        imageViewBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_allcategories, container, false);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesList.clear();
        categoriesList.addAll(categories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        View rootView = requireActivity().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.parseColor("#FF6D00"));
        snackbar.setTextColor(Color.WHITE);
        snackbar.setAction("OK", v -> snackbar.dismiss());
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();

    }

    @Override
    public void showLoading() {
             if (loadingDialog == null) {
                loadingDialog = new Dialog(requireContext());
                loadingDialog.setContentView(R.layout.dialog_loading);
                loadingDialog.setCancelable(false);
                loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
            loadingDialog.show();
        }
    @Override
    public void hideLoading() {

        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}