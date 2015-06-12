package br.com.nglauber.intelsoftwaredaydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.nglauber.intelsoftwaredaydemo.model.Livro;


public class DetalheLivroFragment extends Fragment {

    private Livro livro;
    private MenuItem menuItem;

    @Inject
    Bus mBus;

    public static DetalheLivroFragment novaInstancia(Livro livro){
        DetalheLivroFragment dlf = new DetalheLivroFragment();
        Bundle args = new Bundle();
        args.putSerializable("livro", livro);
        dlf.setArguments(args);
        return dlf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((App)getActivity().getApplication()).getObjectGraph().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.livro = (Livro)getArguments().getSerializable("livro");

        View view = inflater.inflate(R.layout.fragment_detalhe_livro, container, false);

        ImageView imgCapa = (ImageView)view.findViewById(R.id.imgCapa);
        TextView txtTitulo = (TextView)view.findViewById(R.id.txtTitulo);
        TextView txtAno = (TextView)view.findViewById(R.id.txtAno);
        TextView txtAutor = (TextView)view.findViewById(R.id.txtAutor);
        TextView txtPaginas = (TextView)view.findViewById(R.id.txtPaginas);

        Picasso.with(getActivity()).load(livro.capa).into(imgCapa);
        txtTitulo.setText(livro.titulo);
        txtAno.setText(String.valueOf(livro.ano));
        txtAutor.setText(livro.autor);
        txtPaginas.setText(String.valueOf(livro.paginas));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_livro, menu);

        menuItem = menu.findItem(R.id.action_favorito);
        if (isFavorito(this.livro.titulo) == null){
            menuItem.setIcon(R.drawable.ic_star_border_white_48dp);
        } else {
            menuItem.setIcon(R.drawable.ic_star_white_48dp);
        }

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(shareItem);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT, livro.titulo + ", " + livro.ano + ", " + livro.autor);
        shareActionProvider.setShareIntent(it);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorito){

            Livro favorito = isFavorito(this.livro.titulo);

            if (favorito == null) {
                Livro livroFavorito = new Livro(
                        livro.titulo,
                        livro.autor,
                        livro.ano,
                        livro.paginas,
                        livro.capa
                );
                livroFavorito.save();

            } else {
                favorito.delete();
            }
            mBus.post(livro);

            getActivity().invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Livro isFavorito(String titulo){
        return new Select()
                .from(Livro.class)
                .where(Livro.COLUNA_TITULO +" = ?", titulo)
                .executeSingle();
    }
}
