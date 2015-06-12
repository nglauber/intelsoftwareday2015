package br.com.nglauber.intelsoftwaredaydemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import br.com.nglauber.intelsoftwaredaydemo.model.Livro;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class ListaFavoritosFragment extends Fragment {

    @InjectView(R.id.recyclerViewFavoritos)
    RecyclerView mRecyclerView;

    @Inject
    Bus mBus;

    boolean doRefresh;

    List<Livro> mLivros;

    public ListaFavoritosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        ((App)getActivity().getApplication()).getObjectGraph().inject(this);
        mBus.register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (doRefresh){
            preencherLista();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);
        ButterKnife.inject(this, view);

        RecyclerView.LayoutManager lm;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            lm = new LinearLayoutManager(getActivity());
        } else {
            lm = new GridLayoutManager(getActivity(), 2);
        }
        mRecyclerView.setLayoutManager(lm);

        preencherLista();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Subscribe
    public void chegouEvento(Livro livro) {
        doRefresh = true;
    }

    private void preencherLista() {
        mLivros = new Select().from(Livro.class).execute();
        LivrosAdapter adapter = new LivrosAdapter(getActivity(), mLivros);
        adapter.setAoClicarNoLivroListener((AoClicarNoLivroListener)getActivity());
        mRecyclerView.setAdapter(adapter);
    }
}
