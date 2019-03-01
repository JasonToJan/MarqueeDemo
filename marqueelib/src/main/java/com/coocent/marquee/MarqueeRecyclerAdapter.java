package com.coocent.marquee;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

public class MarqueeRecyclerAdapter extends RecyclerView.Adapter<MarqueeRecyclerAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<MarqueeEntity> marqueeLists;
    private static OnListener onListener;
    private static int currentPosition = -1;

    public MarqueeRecyclerAdapter(Context context, ArrayList<MarqueeEntity> marqueeLists) {
        this.context = context;
        this.marqueeLists = marqueeLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.marquee_item, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        int colorNumber = Color.parseColor("#FFFFFF");
//        int colorNumber = t.getNavItem() == 0 ? Color.parseColor("#000000") : Color.parseColor("#FFFFFF");
        if (marqueeLists.size() == i){
            viewHolder.addRelLayout.setVisibility(View.VISIBLE);
            viewHolder.contentLinLayout.setVisibility(View.GONE);
            viewHolder.addTv.setTextColor(colorNumber);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
                viewHolder.addTv.setCompoundDrawablesRelativeWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.marquee_ic_revolving_lamp_color_add), null, null, null);
            }
//            viewHolder.addTv.setCompoundDrawablesRelativeWithIntrinsicBounds(context.getResources().getDrawable(t.getNavItem() == 0 ? R.drawable.ic_revolving_lamp_color_add_black : R.drawable.marquee_ic_revolving_lamp_color_add), null, null, null);
            if (onListener != null){
                viewHolder.addRelLayout.setEnabled(true);
            } else {
                viewHolder.addRelLayout.setEnabled(false);
            }
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                viewHolder.addRelLayout.setBackgroundResource(t.getBtnRipple());
//            }
        } else {
            viewHolder.addRelLayout.setVisibility(View.GONE);
            viewHolder.contentLinLayout.setVisibility(View.VISIBLE);
            if (i == 0 || i == 1){
                viewHolder.deleteImg.setVisibility(View.GONE);
            } else {
                viewHolder.deleteImg.setVisibility(View.VISIBLE);
            }

            if (currentPosition == i){
                viewHolder.nameTv.setVisibility(View.GONE);
                viewHolder.nameEt.setVisibility(View.VISIBLE);

//                viewHolder.nameEt.setHighlightColor(Color.parseColor("#70"+ AllFragment.color[SystemUtil.mCurThemePosition]));
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null){
                    imm.showSoftInput(viewHolder.nameEt, InputMethodManager.RESULT_SHOWN);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

                viewHolder.nameEt.setText(marqueeLists.get(i).getName());
                viewHolder.nameEt.setFocusable(true);
                viewHolder.nameEt.setFocusableInTouchMode(true);
                viewHolder.nameEt.requestFocus();
                viewHolder.nameEt.setSelectAllOnFocus(true);

            } else {
                viewHolder.nameTv.setVisibility(View.VISIBLE);
                viewHolder.nameEt.setVisibility(View.GONE);
                viewHolder.nameEt.clearFocus();
            }

//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//            }

            viewHolder.nameTv.setText(marqueeLists.get(i).getName());
            viewHolder.marqueeBorderView.setBackgroundColor(Color.parseColor(marqueeLists.get(i).getColor()));
            viewHolder.nameTv.setTextColor(colorNumber);
            viewHolder.nameEt.setTextColor(colorNumber);
            viewHolder.deleteImg.setImageResource(R.drawable.marquee_ic_revolving_lamp_color_delete);
//            viewHolder.deleteImg.setImageResource(t.getNavItem() == 0 ? R.drawable.ic_revolving_lamp_color_delete_black:R.drawable.marquee_ic_revolving_lamp_color_delete);
        }
    }

    @Override
    public int getItemCount() {
        return marqueeLists.size()+1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final MarqueeBorderView marqueeBorderView;
        private final TextView nameTv;
        private final ImageView pickerImg, deleteImg;
        private final LinearLayout contentLinLayout;
        private final RelativeLayout addRelLayout;
        private final TextView addTv;
        private final EditText nameEt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentLinLayout = itemView.findViewById(R.id.contentLinLayout);
            marqueeBorderView = itemView.findViewById(R.id.borderView);
            marqueeBorderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListener != null)
                        onListener.onColorPickerClick(getAdapterPosition());
                }
            });
            nameTv = itemView.findViewById(R.id.nameTv);
            nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListener != null){
                        currentPosition = getAdapterPosition();
                        onListener.onTextClick(getAdapterPosition());
                    }
                }
            });
            nameEt = itemView.findViewById(R.id.nameEt);
            nameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
//                    Log.e("TAGF","onFocusChange="+hasFocus);
                    if (onListener != null){
                        if (!hasFocus){
                            currentPosition = -1;
                            onListener.onEditClick(v, getAdapterPosition());
                        }
                    }
                }
            });
//            nameEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    return false;
//                }
//            });
            pickerImg = itemView.findViewById(R.id.pickerImg);
            deleteImg = itemView.findViewById(R.id.deleteImg);
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListener != null){
                        onListener.onDeleteClick(getAdapterPosition());
                    }
                }
            });
            pickerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListener != null)
                        onListener.onColorPickerClick(getAdapterPosition());
                }
            });
            addRelLayout = itemView.findViewById(R.id.addRelLayout);
            addRelLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListener != null){
                        onListener.onAddClick(getAdapterPosition());
                    }
                }
            });
            addTv = itemView.findViewById(R.id.addTv);
        }
    }

    public void setOnListener(OnListener onListener){
        this.onListener = onListener;
    }

    public interface OnListener {
        void onColorPickerClick(int position);
        void onAddClick(int position);
        void onTextClick(int position);
        void onEditClick(View v, int position);
        void onDeleteClick(int position);
    }
}
