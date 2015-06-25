package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.Product;

import java.util.ArrayList;

/**
 * Created by Abhinav on 6/18/2015.
 */
public class AdapterTopProducts extends RecyclerView.Adapter<AdapterTopProducts.ViewHolderBoxOffice> {

    private ArrayList<Product> productArrayList =new ArrayList<Product>();
    private LayoutInflater layoutInflater;
    private AppController volleySingleton;
    private ImageLoader imageLoader;

    public AdapterTopProducts(Context context) {
        layoutInflater=LayoutInflater.from(context);
        volleySingleton=AppController.getInstance();
        imageLoader=volleySingleton.getImageLoader();
    }

    public void setMovieList(ArrayList<Product> listMovies) {
        this.productArrayList =listMovies;
        notifyItemRangeChanged(0,listMovies.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_top_item,parent,false);
        ViewHolderBoxOffice viewHolder=new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Product currentProduct= productArrayList.get(position);
        holder.movieTitle.setText(currentProduct.getpName());
        holder.movieReleaseDate.setText(currentProduct.getpPrice());
//        holder.movieAudienceScore.setRating(currentProduct.getAudienceScore()/20.0f);
        String urlThumbnail = currentProduct.getpUrlThumbnail();
        if(urlThumbnail!=null) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;


        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail=(ImageView)itemView.findViewById(R.id.movieThumbnail);
            movieTitle= (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
//            movieAudienceScore= (RatingBar) itemView.findViewById(R.id.movieAudienceScore);

        }
    }
}




















