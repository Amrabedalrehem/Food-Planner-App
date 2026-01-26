package com.example.foodplanner.ui.home.home.presentation.Details;

import com.example.foodplanner.data.datasource.remote.NetworkCallback;
import com.example.foodplanner.data.models.remote.Meal;
import com.example.foodplanner.data.repository.home.details.DetailsRepository;

import java.util.List;
public class DetailsPresenter implements DetailsContract.Presenter {
    DetailsContract.View view;
    String id;
    DetailsRepository detailsRepository;

    public DetailsPresenter(String id, DetailsContract.View view) {
        this.id = id;
        this.detailsRepository = DetailsRepository.getInstance();
        this.view = view;
    }

    @Override
    public void getDetails() {
        if (view != null) {
            view.showLoading();
        }

        detailsRepository.getDetails(id, new NetworkCallback<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> data) {
                if (view != null) {
                    view.hideLoading();
                    if (data != null && !data.isEmpty()) {
                         view.showDetails(data);
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (view != null) {
                    view.hideLoading();
                    view.showError(errorMessage);
                }
            }
        });
    }
}