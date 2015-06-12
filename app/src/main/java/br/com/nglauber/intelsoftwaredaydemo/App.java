package br.com.nglauber.intelsoftwaredaydemo;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import br.com.nglauber.intelsoftwaredaydemo.module.AppModule;
import dagger.ObjectGraph;

public class App extends Application {

    ObjectGraph objectGraph;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        objectGraph =
                ObjectGraph.create(new AppModule());
    }

    public ObjectGraph getObjectGraph(){

        return objectGraph;
    }
}
