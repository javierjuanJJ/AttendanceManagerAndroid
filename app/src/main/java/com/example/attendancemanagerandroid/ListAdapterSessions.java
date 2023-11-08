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

public class ListAdapterSessions extends BaseAdapter {

   private ArrayList<Session> sessions;
   private Context context;
   private TextView tvText;
   private int idClass;

   public ListAdapterSessions(ArrayList<Session> sessions, Context context, int idClass) {
      this.sessions = sessions;
      this.context = context;
      this.idClass = idClass;
   }

   @Override
   public int getCount() {
      return sessions.size();
   }

   @Override
   public Object getItem(int i) {
      return sessions.get(i);
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
      Session student = sessions.get(i);
      tvText.setText(student.getSubject() + " - " + student.getDate());
      tvText.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View view) {
            Intent intent = new Intent(context, SessionEditor.class);
            Session aClass = sessions.get(i);
            intent.putExtra("Class Id", idClass);
            intent.putExtra("Session Id", aClass.getId());
            context.startActivity(intent);
            return true;
         }
      });

      return inflate;
   }


}
