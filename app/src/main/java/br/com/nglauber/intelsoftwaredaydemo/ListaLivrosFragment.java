package br.com.nglauber.intelsoftwaredaydemo;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.nglauber.intelsoftwaredaydemo.model.Categoria;
import br.com.nglauber.intelsoftwaredaydemo.model.Editora;
import br.com.nglauber.intelsoftwaredaydemo.model.Livro;
import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.Lazy;


public class ListaLivrosFragment extends Fragment {

    @InjectView(R.id.recyclerViewWeb)
    RecyclerView mRecyclerView;

    @Inject
    Lazy<LivroHttpService> livroService;

    Editora mEditora;
    DownloadLivrosTask mTask;

    public ListaLivrosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        ((App)getActivity().getApplication()).getObjectGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        RecyclerView.LayoutManager lm;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            lm = new LinearLayoutManager(getActivity());
        } else {
            lm = new GridLayoutManager(getActivity(), 2);
        }
        mRecyclerView.setLayoutManager(lm);

        if (mEditora == null){
            if (mTask == null){
                mTask = new DownloadLivrosTask();
                mTask.execute();
            } else if (mTask.getStatus() == AsyncTask.Status.FINISHED){
                preencherLista();
            }
        } else {
            preencherLista();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    class DownloadLivrosTask extends AsyncTask<Void, Void, Editora>{

        @Override
        protected Editora doInBackground(Void... voids) {
            mEditora = livroService.get().carregarEditora();
            return mEditora;
        }

        @Override
        protected void onPostExecute(Editora editora) {
            super.onPostExecute(editora);
            preencherLista();
        }
    }

    private void preencherLista() {
        if (mEditora != null) {
            List<Livro> livros = new ArrayList<>();
            for (Categoria categoria : mEditora.categorias) {
                livros.addAll(categoria.livros);
            }

            LivrosAdapter adapter = new LivrosAdapter(getActivity(), livros);
            adapter.setAoClicarNoLivroListener((AoClicarNoLivroListener) getActivity());
            mRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), R.string.erro_carregar_livro, Toast.LENGTH_SHORT).show();
        }
    }
}
