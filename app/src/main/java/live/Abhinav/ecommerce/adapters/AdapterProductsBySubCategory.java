package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.ProductBySubCategory;

import java.util.ArrayList;

/**
 * Created by abhin on 7/21/2015.
 */
public class AdapterProductsBySubCategory extends RecyclerView.Adapter<AdapterProductsBySubCategory.ViewHolderProducts> {

    private ArrayList<ProductBySubCategory> productArrayList =new ArrayList<ProductBySubCategory>();
    private LayoutInflater layoutInflater;
    private AppController volleySingleton;
    private ImageLoader imageLoader;

    public AdapterProductsBySubCategory(Context context) {
        layoutInflater=LayoutInflater.from(context);
        volleySingleton=AppController.getInstance();
        imageLoader=volleySingleton.getImageLoader();
    }

    public void setTopProductList(ArrayList<ProductBySubCategory> productArrayList) {
        this.productArrayList =productArrayList;
        notifyItemRangeChanged(0, productArrayList.size());
    }

    @Override
    public ViewHolderProducts onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.row_product_by_category,parent,false);
        ViewHolderProducts viewHolder=new ViewHolderProducts(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderProducts holder, int position) {
        ProductBySubCategory currentProduct= productArrayList.get(position);
        holder.productName.setText(currentProduct.getpName());
        holder.productPrice.setText(currentProduct.getpPrice());
//        holder.movieAudienceScore.setRating(currentProduct.getAudienceScore()/20.0f);
        String urlThumbnail = currentProduct.getpUrlThumbnail();
        if(urlThumbnail!=null) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                    holder.productThumbnail.setImageBitmap(response.getBitmap());
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

    static class ViewHolderProducts extends RecyclerView.ViewHolder {

        private ImageView productThumbnail;
        private TextView productName;
        private TextView productPrice;


        public ViewHolderProducts(View itemView) {
            super(itemView);
            productThumbnail =(ImageView)itemView.findViewById(R.id.productThumbnail);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
//            movieAudienceScore= (RatingBar) itemView.findViewById(R.id.movieAudienceScore);

        }
    }
}
