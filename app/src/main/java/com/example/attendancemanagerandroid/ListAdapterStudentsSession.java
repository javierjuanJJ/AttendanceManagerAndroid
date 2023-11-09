package com.example.attendancemanagerandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class ListAdapterStudentsSession extends BaseAdapter {

   private ArrayList<Student> classes;
   private ArrayList<Integer> sessionStudents;
   private Context context;
   private CheckBox tvText;
   private int idClass;
   private ArrayList<Integer> insertStudents, deletedStudents;

   public ListAdapterStudentsSession(ArrayList<Student> classes,ArrayList<Integer> insertStudents,ArrayList<Integer> deletedStudents,ArrayList<Integer> sessionStudents, Context context, int idClass) {
      this.classes = classes;
      this.sessionStudents = sessionStudents;
      this.context = context;
      this.idClass = idClass;
      this.insertStudents = insertStudents;
      this.deletedStudents = deletedStudents;
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
      @SuppressLint("ViewHolder") View inflate = inflater.inflate(R.layout.list_item_checkbox, null);

      tvText = inflate.findViewById(R.id.tvText);
      Student student = classes.get(i);
      tvText.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));

      int srudentId = student.getId();

      tvText.setChecked(sessionStudents.contains(srudentId));
      tvText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b && !sessionStudents.contains(srudentId)){
               addStudent(srudentId);
            } else if (!b && sessionStudents.contains(srudentId)) {
               removeStudent(srudentId);
            } else if (b && sessionStudents.contains(srudentId)) {
               if (deletedStudents.contains(srudentId) && deletedStudents.size() != 1){
                  deletedStudents.remove(srudentId);
               }
               else {
                  deletedStudents = new ArrayList<>();
               }
            }
         }
      });

      return inflate;
   }


   private void addStudent(int id){
      if (!insertStudents.contains(id)) insertStudents.add(id);
      if (deletedStudents.contains(id)) deletedStudents.remove(id);
   }
   private void removeStudent(int id){
      if (insertStudents.contains(id)) insertStudents.remove(id);
      if (!deletedStudents.contains(id)) deletedStudents.add(id);
   }

}
