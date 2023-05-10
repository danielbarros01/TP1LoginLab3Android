package ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ViewModelMain extends AndroidViewModel {

    private Context context;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
}
