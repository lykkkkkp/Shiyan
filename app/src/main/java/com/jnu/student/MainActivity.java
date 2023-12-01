package com.jnu.student;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.data.BookItem;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mainRecyclerview=findViewById(R.id.recycle_view_books);
        mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<BookItem> bookItems=new ArrayList<>();
        bookItems.add(new BookItem("软件项目管理案例教程（第4版）",R.drawable.book_2));
        bookItems.add(new BookItem("创新工程实践",R.drawable.book_no_name));
        bookItems.add(new BookItem("信息安全数学基础（第2版）",R.drawable.book_1));

        BookItemAdapter bookItemAdapter =new BookItemAdapter(bookItems);
        mainRecyclerview.setAdapter(bookItemAdapter);

        registerForContextMenu(mainRecyclerview);

        addItemLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        String name = data.getStringExtra("name");
                        bookItems.add(new BookItem(name,R.drawable.book_2));
                        bookItemAdapter.notifyItemInserted(bookItems.size());

                        //获取返回的数据//在这塑可以根据需要进行进一步处理
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

    }
    ActivityResultLauncher<Intent> addItemLauncher;
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case 0:

                Intent intent = new Intent(MainActivity.this,BookItemDetailsActivity.class);
                addItemLauncher.launch(intent);
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

        private ArrayList<BookItem> bookItemArrayList;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder)
         */
            public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
                private final TextView textViewName;
                    private final ImageView ImageViewNameItem;
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.setHeaderTitle("具体操作");
                        menu.add( 0,0, this.getAdapterPosition(),"添加"+this.getAdapterPosition());
                        menu.add(0,1, this.getAdapterPosition(),"删除"+this.getAdapterPosition());
                        menu.add(0,2,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
                    }
            public ViewHolder(View bookitemView) {
                        super(bookitemView);
                        // Define click listener for the ViewHolder's View

                        textViewName = bookitemView.findViewById(R.id.text_view_book_title);
                        ImageViewNameItem = bookitemView.findViewById(R.id.bookView_item1);
                        bookitemView.setOnCreateContextMenuListener(this);
                    }

            public TextView getTextViewName() {
                return textViewName;
            }
            public ImageView getImageViewNameItem() {
                return ImageViewNameItem;
            }


        }


                        /**
                         * Initialize the dataset of the Adapter
                         *
                         * @param dataSet String[] containing the data to populate views to be used
                         * by RecyclerView
                         */
        public BookItemAdapter(ArrayList<BookItem> dataSet) {
                            bookItemArrayList = dataSet;
                        }

                        // Create new views (invoked by the layout manager)
                        @Override
                        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                            // Create a new view, which defines the UI of the list item
                            View view = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.book_item_row, viewGroup, false);
                            return new ViewHolder(view);
                        }



                        // Replace the contents of a view (invoked by the layout manager)
                        @Override
                        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

                            // Get element from your dataset at this position and replace the
                            // contents of the view with that element
                            viewHolder.getTextViewName().setText(bookItemArrayList.get(position).getName());
                            viewHolder.getImageViewNameItem().setImageResource(bookItemArrayList.get(position).getImageId());
                        }

                        // Return the size of your dataset (invoked by the layout manager)
                        @Override
                        public int getItemCount() {
                            return bookItemArrayList.size();
                        }
                    }

                }