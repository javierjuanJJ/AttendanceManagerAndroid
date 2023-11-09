package com.example.attendancemanagerandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterStudents2 extends BaseAdapter {

   private ArrayList<Student> classes;
   private Context context;
   private TextView tvText;
   private int idClass;

   public ListAdapterStudents2(ArrayList<Student> classes, Context context, int idClass) {
      this.classes = classes;
      this.context = context;
      this.idClass = idClass;
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
      Student student = classes.get(i);
      tvText.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));
      tvText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(context, ClassEditor.class);
            Student aClass = classes.get(i);
            intent.putExtra("Class Id", idClass);
            intent.putExtra("Student Id", aClass.getId());

         }
      });

      return inflate;
   }



}
