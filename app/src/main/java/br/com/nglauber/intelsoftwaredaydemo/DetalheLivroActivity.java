package br.com.nglauber.intelsoftwaredaydemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.nglauber.intelsoftwaredaydemo.model.Livro;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetalheLivroActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhe_livro);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.titulo_detalhe);
        mToolbar.setTitleTextColor(Color.WHITE);

        if (savedInstanceState == null) {
            Livro livro = (Livro) getIntent().getSerializableExtra("livro");

            DetalheLivroFragment detalheLivroFragment =
                    DetalheLivroFragment.novaInstancia(livro);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheLivroFragment, "detalhe")
                    .commit();
        }
    }
}
