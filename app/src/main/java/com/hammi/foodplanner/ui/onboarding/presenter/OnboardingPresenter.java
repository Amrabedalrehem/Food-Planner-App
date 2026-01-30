package com.hammi.foodplanner.ui.onboarding.presenter;

import android.content.Context;

import com.hammi.foodplanner.data.repository.sharedprefs.SharedPrefsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnboardingPresenter implements OnboardingContract.Presenter {

    private OnboardingContract.View view;
    private SharedPrefsRepository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public OnboardingPresenter(OnboardingContract.View view, Context context) {
        this.view = view;
        this.repository = new SharedPrefsRepository(context.getApplicationContext());
    }

    @Override
    public void completeOnboarding() {
        if (view == null) return;

        disposables.add(
                repository.markOnboardingComplete()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    if (view != null) view.navigateToAuthentication();
                                },
                                throwable -> {
                                     if (view != null) view.navigateToAuthentication();
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        disposables.clear();
        view = null;
        repository = null;
    }
}
