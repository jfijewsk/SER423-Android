package edu.asu.bsse.jfijewsk.lab7;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

/*
PlaceListAdapter.java
 Assign7jfijewsk
 Created by James on 3/18/20.
 Copyright © 2020 James. All rights reserved.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 @author   James Fijewski   mailto:jfijewsk@asu.edu.
 @version March 30, 2020
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> implements
        View.OnClickListener {

    private HashMap<String,String> courses;
    private String lastSelectedCourse = "";
    private View lastSelectedView = null;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView aPrefixTV;
        public TextView aTitleTV;
        public RelativeLayout parentView;

        public ViewHolder(View aView){
            super(aView);
            android.util.Log.d(this.getClass().getSimpleName()," viewholder constuctor called, calling findByView");
            parentView = ((RelativeLayout)aView.findViewById(R.id.rl));
            aPrefixTV = (TextView)parentView.findViewById(R.id.course_name);
            aTitleTV = (TextView)parentView.findViewById(R.id.course_title);
        }
    }

    public PlaceListAdapter(HashMap<String,String> courses){
        this.courses = courses;
    }

    @Override
    public PlaceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return(new PlaceListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.a_text_view, parent,
                false)));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String[] theCourses = courses.keySet().toArray(new String[]{});
        Arrays.sort(theCourses);
        holder.aPrefixTV.setOnClickListener(this);
        holder.aPrefixTV.setText(theCourses[position]);
        holder.aTitleTV.setText(courses.get(theCourses[position]));
    }

    @Override
    public int getItemCount(){
        int count = courses.keySet().toArray().length;
        //android.util.Log.d(this.getClass().getSimpleName()," itemcount returning: "+count);
        return count;
    }

    public void onClick(View v){
        TextView atv = (TextView)v.findViewById(R.id.course_name);
        String aCrs = atv.getText().toString();
        v.setBackgroundResource(R.color.dark_blue);
        if(lastSelectedCourse != "" && lastSelectedView != v){
            lastSelectedView.setBackgroundResource(R.color.light_blue);
        }
        //android.util.Log.d(this.getClass().getSimpleName(),"called onClick " + aCrs +
       // " open " + courses.get(aCrs));
        lastSelectedView = v;
        lastSelectedCourse = aCrs;
    }
}
