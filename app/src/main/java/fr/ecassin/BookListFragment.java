package fr.ecassin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class BookListFragment extends Fragment {

    private ArrayList<Book> books = new ArrayList<>();
    private BookAdapter bookAdapter;
    private OnBookClickedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnBookClickedListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookAdapter = new BookAdapter(LayoutInflater.from(getContext()), books, listener);

        RecyclerView recyclerView = view.findViewById(R.id.bookRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(bookAdapter);

        if (savedInstanceState != null){
            books.clear();
            ArrayList<Book> booksToAdd = savedInstanceState.getParcelableArrayList(getResources().getString(R.string.books_key));
            books.addAll(booksToAdd);

            // Notify that the books' data has changed
            bookAdapter.notifyDataSetChanged();

        } else if (books.isEmpty()){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final BookService bookService = retrofit.create(BookService.class);

            Call<List<Book>> listCall = bookService.getBooks();
            Timber.i("Retrieving books from WebService");

            listCall.enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    books.clear();
                    List<Book> booksToAdd = response.body();
                    books.addAll(booksToAdd);

                    // Notify that the books' data has changed
                    bookAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    Timber.e(t, "Error while trying to get books");
                }
            });
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getResources().getString(R.string.books_key), books);
    }


    public interface OnBookClickedListener {
        void onBookClicked(Book book);
    }
}
