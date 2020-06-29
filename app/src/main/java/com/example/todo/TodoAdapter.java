package com.example.todo;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todo.ROOM.Todo;
import com.example.todo.Widgets.DoubleClickListener;
import com.example.todo.sort.ComparatorDueDate;
import com.example.todo.sort.CompareRecent;

import java.text.DateFormat;
import java.util.*;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    private List<Todo> todos = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todolayout2, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo current = todos.get(position);
        Calendar c = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        long cTime = c.getTimeInMillis();
        long nowTime = now.getTimeInMillis();

        //Bundle extras = EditAddTodoActivity.getIntent().getExtras();

        c.set(current.getDue_year(), current.getDue_month(), current.getDue_day(), current.getDue_hour(), current.getDue_minute());

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        holder.text_view_tile.setText(current.getTitle());
        holder.text_view_content.setText(current.getContent());
        holder.check_important.setText(String.valueOf(current.isImportaint()));

       // holder.text_view_tile.setText(extras.getBoolean);

        holder.text_view_days.setText(currentDateString);
        holder.text_view_time.setText(String.format("%02d", current.getDue_hour()) + ":" + String.format("%02d", current.getDue_minute()));

        if (cTime < nowTime){
            holder.text_view_days.setTextColor(Color.RED);
            holder.text_view_time.setTextColor(Color.RED);
        }


        //holder.text_view_days.setText(String.valueOf(cTime));
        //holder.text_view_time.setText(String.valueOf(nowTime));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        Collections.sort(todos, new CompareRecent());
        notifyDataSetChanged();
    }

    public List<Todo> getTodos(){
        return this.todos;
    }

    public Todo getTodoAt(int position) {
        return todos.get(position);
    }

    public void sortByDue(){
        Collections.sort(todos, new ComparatorDueDate());
        //setTodos(todos);
    }

    //Holder Class for the top viewer (TOP)
    class TodoHolder extends RecyclerView.ViewHolder {
        private TextView text_view_tile;
        private TextView text_view_content;
        private TextView check_important;
        private TextView text_view_days;
        private TextView text_view_time;

        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            text_view_tile = itemView.findViewById(R.id.text_view_title);
            text_view_content = itemView.findViewById(R.id.text_view_content);
            check_important = itemView.findViewById(R.id.check_important);
            text_view_days = itemView.findViewById(R.id.text_view_date);
            text_view_time = itemView.findViewById(R.id.text_view_time);
/*            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                        onItemClickListener.OnItemClick(todos.get(position));
                }

            });*/
            itemView.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                        onItemClickListener.OnItemClick(todos.get(position));
                }

                @Override
                public void onDoubleClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                        onItemClickListener.OnItemClick(todos.get(position));

                }
            });

        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Todo todo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}


