package fr.ecassin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import timber.log.Timber;


public class LibraryActivity extends AppCompatActivity implements BookListFragment.OnBookClickedListener {

    private Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Timber.plant(new Timber.DebugTree());

        BookListFragment bookFragment;

        if(savedInstanceState != null){
            // We are in book detail page
            bookFragment = (BookListFragment) getSupportFragmentManager().findFragmentByTag(BookListFragment.class.getSimpleName());

            selectedBook = savedInstanceState.getParcelable(getResources().getString(R.string.book_key));

            displayBookDetail();
        } else {
            // We are in book list page
            bookFragment = new BookListFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, bookFragment, BookListFragment.class.getSimpleName())
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getResources().getString(R.string.book_key), selectedBook);
    }


    @Override
    public void onBookClicked(Book book) {
        selectedBook = book;
        displayBookDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displayBookDetail(){
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        BookDetailFragment detailFragment = new BookDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(getResources().getString(R.string.book_key), selectedBook);

        detailFragment.setArguments(bundle);

        if(isLandscape){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bookDetail, detailFragment, BookDetailFragment.class.getSimpleName())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerFrameLayout, detailFragment, BookDetailFragment.class.getSimpleName())
                    .addToBackStack("ComingFromList")
                    .commit();
        }

    }
}
