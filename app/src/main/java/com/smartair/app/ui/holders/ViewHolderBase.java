package com.smartair.app.ui.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public abstract class ViewHolderBase {

    protected abstract void initHolder(@NotNull View v);


    /**
     * Construct a new view holder for the supplied view's visual hierarchy.
     *
     * @param v     The view, whose visual hierarchy will be hold by the newly constructed holder instance.
     */
    protected ViewHolderBase(@NotNull View v) {
        initHolder(v);
    }


    public static abstract class ViewCreator {
        private final LayoutInflater layoutInflater;

        protected ViewCreator(@NotNull Context context) {
            layoutInflater = LayoutInflater.from(context);
        }


        @NotNull
        public View create(@NotNull ViewGroup parent) {
            //noinspection ResourceType
            View holderView = layoutInflater.inflate(getHolderViewResourceId(), parent, false);

            assert holderView != null;
            holderView.setTag(getNewHolderInstance(holderView));

            return holderView;
        }

        protected abstract int getHolderViewResourceId();
        protected abstract ViewHolderBase getNewHolderInstance(@NotNull View view);
    }

    public static ViewCreator initCreator(Context context) {
        throw new Error("initCreator has to be overloaded!");
    }

    @SuppressWarnings("unchecked")
    public static <T extends ViewHolderBase> T retrieve(@NotNull View v) {
        return (T) v.getTag();
    }
}
