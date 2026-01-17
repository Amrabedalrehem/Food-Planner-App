package com.example.foodplanner.ui.auth;

public interface AuthContract {
     interface View {
        void showWelcomeMessage(String name);
        void showError(String message);
        void navigateToHome();
          void openSignUp();
         void openSignIn();
     }
    interface Presenter {
        void loginWithGoogle(String idToken);
        void registerWithEmailAndPassword(String email,String password);
        void loginWithEmailAndPassword(String email, String password);
      }
}


