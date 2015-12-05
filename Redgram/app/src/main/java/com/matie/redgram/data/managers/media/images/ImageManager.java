package com.matie.redgram.data.managers.media.images;

import android.content.Context;
import android.graphics.PointF;
import android.media.Image;
import android.net.Uri;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * Created by matie on 2015-11-01.
 */
public class ImageManager {

    public static SingleImageBuilder newImageBuilder(Context context){
        return new SingleImageBuilder(context);
    }

    public static MultipleImageBuilder newMultipleImageBuilder(Context context){
        return new MultipleImageBuilder(context);
    }

    private static GenericDraweeHierarchy buildGenericDraweesHierarchy(Context context) {
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setActualImageFocusPoint(new PointF(0.5f, 0f))
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        return hierarchy;
    }

    private static PipelineDraweeControllerBuilder getControllerBuilderInstance(){
        return Fresco.newDraweeControllerBuilder();
    }

    public static class SingleImageBuilder {

        private SimpleDraweeView imageView;
        private PipelineDraweeControllerBuilder controllerBuilder;
        private GenericDraweeHierarchy genericDraweeHierarchy;
        private ImageRequest mainImageRequest;
        private ImageRequest lowResImageRequest;
        private Context context;

        private SingleImageBuilder(Context context) {
            this.context = context;
            //set the default builders
            this.genericDraweeHierarchy = buildGenericDraweesHierarchy(this.context);
            this.controllerBuilder = getControllerBuilderInstance();
        }

        public SingleImageBuilder setImageView(SimpleDraweeView imageView) {
            this.imageView = imageView;
            return this;
        }

        public SingleImageBuilder setControllerBuilder(PipelineDraweeControllerBuilder controllerBuilder) {
            this.controllerBuilder = controllerBuilder;
            return this;
        }

        public SingleImageBuilder setGenericDraweeHierarchy(GenericDraweeHierarchy genericDraweeHierarchy) {
            this.genericDraweeHierarchy = genericDraweeHierarchy;
            return this;
        }

        public PipelineDraweeControllerBuilder getControllerBuilder() {
            return controllerBuilder;
        }

        public SingleImageBuilder setThumbnail(String url){
            Uri thumbnailUri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(thumbnailUri)
                    .build();
            lowResImageRequest = request;
            controllerBuilder.setImageRequest(request);
            return this;
        }
        public SingleImageBuilder setImage(String url, boolean progressive){
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(progressive)
                    .build();
            mainImageRequest = request;
            controllerBuilder.setLowResImageRequest(request);
            return this;
        }
        public SingleImageBuilder includeOldController(){
            try {
                controllerBuilder.setOldController(imageView.getController());
            }catch (NullPointerException e){
                Log.d("ImageManager", "Image view not found");
            }
            return this;
        }
        public SingleImageBuilder setListener(ControllerListener listener){
            controllerBuilder.setControllerListener(listener);
            return this;
        }
        public void build(){
            DraweeController controller = controllerBuilder.build();
            try{
                imageView.setController(controller);
                imageView.setHierarchy(genericDraweeHierarchy);
            }catch (NullPointerException e){
                Log.d("ImageManager", "Image view not found");
            }

        }
    }

    public static class MultipleImageBuilder{
        private List<SimpleDraweeView> viewList;
        private Context context;
        private GenericDraweeHierarchy genericDraweeHierarchy;

        public MultipleImageBuilder(Context context) {
            this.context = context;
            genericDraweeHierarchy = buildGenericDraweesHierarchy(this.context);
        }

        public void setViewList(List<SimpleDraweeView> viewList) {
            this.viewList = viewList;
        }

    }
}