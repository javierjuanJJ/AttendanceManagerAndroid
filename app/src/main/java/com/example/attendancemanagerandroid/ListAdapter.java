package com.example.attendancemanagerandroid;

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
      View inflate = inflater.inflate(R.layout.list_item, null);

      tvText = inflate.findViewById(R.id.tvText);
      tvText.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            Intent intent = new Intent(context, ClassEditor.class);
            Class aClass = classes.get(i);
            intent.putExtra("Class Id", aClass.getId());
            intent.putExtra("Class Id", aClass.getClassName());
            return true;
         }
      });

      return inflate;
   }
}
