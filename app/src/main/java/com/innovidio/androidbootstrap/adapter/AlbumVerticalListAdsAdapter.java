//package com.innovidio.androidbootstrap.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import java.util.ArrayList;ArrayList
//import java.util.List;
//
//public class AlbumVerticalListAdsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int AD_VIEW = 0;
//    private static final int CONTENT_VIEW =  1;
//
//    List<Album> albumList;
//    IAlbumListener iAlbumListener;
//
//    public AlbumVerticalListAdsAdapter(){
//        albumList = new ArrayList<>();
//    }
//
//    public void clearList(){
//        albumList.clear();
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            if (viewType == AD_VIEW){
//                View unifiedNativeLayoutView = LayoutInflater.from(
//                        parent.getContext()).inflate(R.layout.item_app_admob_album_install,
//                        parent, false);
//                return new UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
//            }else {
//                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//                View view = layoutInflater.inflate(R.layout.item_vertical_music, parent, false);
//                return new AlbumVerticalViewHolder(view);
//            }
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        int viewType = getItemViewType(position);
//        switch (viewType) {
//            case AD_VIEW:
//                UnifiedNativeAdViewHolder  unifiedNativeAdViewHolder = (UnifiedNativeAdViewHolder) holder;
//                if(holder.itemView.getTag() != ""){
//                    holder.itemView.setTag("");
//                    Utility.showBigNativeAd2(ResourcesHelper.getString(R.string.native_id), unifiedNativeAdViewHolder.getAdView());
//                }
//                break;
//            case CONTENT_VIEW:
//                final Album album = (Album) albumList.get(position);
//                AlbumVerticalViewHolder albumVerticalViewHolder = (AlbumVerticalViewHolder) holder;
//                albumVerticalViewHolder.binding.setMusic(album);
//                albumVerticalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (iAlbumListener != null)
//                            iAlbumListener.onClick(position, album);
//                    }
//                });
//            /*default:
//                final Album album = albumList.get(position);
//                AlbumVerticalViewHolder albumVerticalViewHolder = (AlbumVerticalViewHolder) holder;
//                albumVerticalViewHolder.binding.setMusic(album);
//                albumVerticalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (iAlbumListener != null)
//                            iAlbumListener.onClick(position, album);
//                    }
//                });*/
//        }
//    }
//
//    public class AlbumVerticalViewHolder extends RecyclerView.ViewHolder {
//        ItemVerticalMusicBinding binding;
//
//        public AlbumVerticalViewHolder(@NonNull View itemView) {
//            super(itemView);
//            binding = DataBindingUtil.bind(itemView);
//        }
//    }
//
//        // for adds Override this method
//        @Override
//        public int getItemViewType(int position) {
//            if(albumList.get(position)==null)
//                return AD_VIEW;
//            return CONTENT_VIEW;
//        }
//
//    @Override
//    public int getItemCount() {
//        return albumList.size();
//    }
//
//    private void populateNativeAdView(UnifiedNativeAd nativeAd,
//                                      UnifiedNativeAdView adView) {
//        // Some assets are guaranteed to be in every UnifiedNativeAd.
//        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
//        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
//       // ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
//
//        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//        // check before trying to display them.
//        /*NativeAd.Image icon = nativeAd.getIcon();
//
//        if (icon == null) {
//            adView.getIconView().setVisibility(View.INVISIBLE);
//        } else {
//            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
//            adView.getIconView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getPrice() == null) {
//            adView.getPriceView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getPriceView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
//        }
//
//        if (nativeAd.getStore() == null) {
//            adView.getStoreView().setVisibility(View.INVISIBLE);
//        } else {
//            adView.getStoreView().setVisibility(View.VISIBLE);
//            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
//        }
//
//        if (nativeAd.getStarRating() == null) {
//            adView.getStarRatingView().setVisibility(View.INVISIBLE);
//        } else {
//            ((RatingBar) adView.getStarRatingView())
//                    .setRating(nativeAd.getStarRating().floatValue());
//            adView.getStarRatingView().setVisibility(View.VISIBLE);
//        }
//
//        if (nativeAd.getAdvertiser() == null) {
//            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//        } else {
//            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//            adView.getAdvertiserView().setVisibility(View.VISIBLE);
//        }*/
//
//        // Assign native ad object to the native view.
//        adView.setNativeAd(nativeAd);
//    }
//
//    public void setAlbumListener(IAlbumListener iAlbumListener) {
//        this.iAlbumListener = iAlbumListener;
//    }
//
//    public void setAlbumList(List<Album> albumList) {
//        this.albumList.clear();
//        this.albumList = albumList;
//        notifyDataSetChanged();
//    }
//}
