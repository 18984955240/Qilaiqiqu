package com.qizhi.qilaiqiqu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qizhi.qilaiqiqu.R;
import com.qizhi.qilaiqiqu.model.CharacterModel;
import com.qizhi.qilaiqiqu.utils.CircleImageViewUtil;
import com.qizhi.qilaiqiqu.utils.SystemUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by leiqian on 2016/5/18.
 */
public class CharacterListAdapter extends BaseAdapter {

    private List<CharacterModel> list;
    private Context context;
    private ViewHolder holder;
    public CharacterListAdapter(List<CharacterModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_character, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SystemUtil.Imagexutils_photo(list.get(position).getUserImage(),holder.txtCharacterPhoto,context);
        SystemUtil.Imagexutils_new(list.get(position).getDefaultImage(),holder.imgCharacterImage,context);
        holder.imgCharacterTitle.setText(list.get(position).getTitle());
        holder.txtCharacterName.setText(list.get(position).getUserName());
        holder.txtCharacterBrowse.setText("("+list.get(position).getScanNum()+")");
        holder.txtCharacterRecomment.setText("("+list.get(position).getComment_num()+")");


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_character_image)
        ImageView imgCharacterImage;
        @InjectView(R.id.img_character_title)
        TextView imgCharacterTitle;
        @InjectView(R.id.txt_character_photo)
        CircleImageViewUtil txtCharacterPhoto;
        @InjectView(R.id.txt_character_name)
        TextView txtCharacterName;
        @InjectView(R.id.txt_character_browse)
        TextView txtCharacterBrowse;
        @InjectView(R.id.txt_character_recomment)
        TextView txtCharacterRecomment;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
