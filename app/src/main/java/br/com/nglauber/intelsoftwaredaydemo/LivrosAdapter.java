package br.com.nglauber.intelsoftwaredaydemo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.nglauber.intelsoftwaredaydemo.databinding.ItemLivroBinding;
import br.com.nglauber.intelsoftwaredaydemo.model.Livro;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class LivrosAdapter extends RecyclerView.Adapter<LivrosAdapter.LivrosViewHolder> {
    private Context mContext;
    private List<Livro> mLivros;
    private AoClicarNoLivroListener mListener;

    public LivrosAdapter(Context ctx, List<Livro> livros) {
        mContext = ctx;
        mLivros = livros;
    }

    @Override
    public LivrosViewHolder onCreateViewHolder(ViewGroup parent,  int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_livro, parent, false);

        LivrosViewHolder vh = new LivrosViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LivrosViewHolder holder, int position) {
        Livro livro = mLivros.get(position);
        holder.mBinding.setLivro(livro);
        holder.mBinding.executePendingBindings();
        Picasso.with(mContext).load(livro.capa).into(holder.mImgCapa);
    }

    @Override
    public int getItemCount() {
        return mLivros != null ? mLivros.size() : 0;
    }

    public void setAoClicarNoLivroListener(AoClicarNoLivroListener l){
        mListener = l;
    }

    public class LivrosViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imgCapa)
        public ImageView mImgCapa;

        private ItemLivroBinding mBinding;

        public LivrosViewHolder(View parent) {
            super(parent);
            ButterKnife.inject(this, parent);

            mBinding = DataBindingUtil.bind(parent);

            mBinding.card.setOnClickListener(mOnClickListener);
        }

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    int position = getAdapterPosition();
                    mListener.aoClicarNoLivro(mLivros.get(position));
                }
            }
        };
    }
}