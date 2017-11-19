package fr.ecassin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.CustomViewHolder> {

    private final LayoutInflater inflater;
    private List<Book> books;
    private BookListFragment.OnBookClickedListener listener;


    BookAdapter(LayoutInflater inflater, List<Book> books, BookListFragment.OnBookClickedListener listener) {
        this.inflater = inflater;
        this.books = books;
        this.listener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.book_fragment, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Book book = books.get(position);

        BookItemView bookItemView = (BookItemView) holder.itemView;
        bookItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBookClicked(book);
            }
        });

        bookItemView.bindView(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    static class CustomViewHolder extends RecyclerView.ViewHolder {
        CustomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
