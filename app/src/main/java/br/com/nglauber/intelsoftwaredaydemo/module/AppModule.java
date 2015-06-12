package br.com.nglauber.intelsoftwaredaydemo.module;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import br.com.nglauber.intelsoftwaredaydemo.DetalheLivroFragment;
import br.com.nglauber.intelsoftwaredaydemo.ListaFavoritosFragment;
import br.com.nglauber.intelsoftwaredaydemo.ListaLivrosFragment;
import br.com.nglauber.intelsoftwaredaydemo.LivroHttpService;
import dagger.Module;
import dagger.Provides;

@Module(injects = {
        ListaFavoritosFragment.class,
        ListaLivrosFragment.class,
        DetalheLivroFragment.class
})
public class AppModule {
    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides
    public LivroHttpService provideLivroHttpService() {
        return new LivroHttpService();
    }
}