package com.example.attendancemanagerandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

   private ArrayList<Class> classes;
   private Context context;
   private TextView tvText;

   public ListAdapter(ArrayList<Class> classes, Context context) {
      this.classes = classes;
      this.context = context;
   }

   @Override
   public int getCount() {
      return classes.size();
   }

   @Override
   public Object getItem(int i) {
      return classes.get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {


      LayoutInflater inflater = LayoutInflater.from(context);
      @SuppressLint("ViewHolder") View inflate = inflater.inflate(R.layout.list_item, null);

      tvText = inflate.findViewById(R.id.tvText);
      tvText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(context, ClassEditor.class);
            intent.putExtra("Class Id", classes.get(i).getId());
            intent.putExtra("Class Name", classes.get(i).getClassName());
            context.startActivity(intent);
         }
      });

      tvText.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            Intent intent = new Intent(context, ClassEditor.class);
            Class aClass = classes.get(i);
            intent.putExtra("Class ID", aClass.getId());
            intent.putExtra("Class Name", aClass.getClassName());
            return true;
         }
      });

      return inflate;
   }
}
