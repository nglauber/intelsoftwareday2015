package br.com.nglauber.intelsoftwaredaydemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.nglauber.intelsoftwaredaydemo.model.Livro;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements AoClicarNoLivroListener {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.pager)
    ViewPager mViewPager;
    @InjectView(R.id.tablayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Color.WHITE);
        mViewPager.setAdapter(new LivrosPageAdapter(
                getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void aoClicarNoLivro(Livro livro) {
        Intent it = new Intent(this, DetalheLivroActivity.class);
        it.putExtra("livro", livro);
        startActivity(it);
    }

    private class LivrosPageAdapter extends FragmentPagerAdapter {
        public LivrosPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(position == 0 ? R.string.tab_livros : R.string.tab_favoritos);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0){
                return new ListaLivrosFragment();
            } else {
                return new ListaFavoritosFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
